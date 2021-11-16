package com.strongshop.mobile.controller;

import com.google.protobuf.Api;
import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.dto.Contract.ContractConstructionImageResponseDto;
import com.strongshop.mobile.dto.Contract.ContractInspectionImageResponseDto;
import com.strongshop.mobile.dto.Contract.ContractRequestDto;
import com.strongshop.mobile.dto.Contract.ContractResponseDto;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.BiddingService;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.ContractService;
import com.strongshop.mobile.service.FileUploadService;
import com.strongshop.mobile.service.OrderService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ContractController {

    private final OrderService orderService;
    private final BiddingService biddingService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyService companyService;
    private final ContractService contractService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final FileUploadService fileUploadService;

    //TODO orderId와 biddingId를 받아서 contract생성, order의 상태, contract상태 변경 필요. Bidding상태도 변경.(POST)
    @PostMapping("/api/contract")
    @Transactional
    public ResponseEntity<ApiResponse<ContractResponseDto>> registerContract(@RequestBody ContractRequestDto requestDto)
    {
        Order order = orderService.getOrderByOrderId(requestDto.getOrder_id());
        Bidding bidding = biddingService.getBiddingByBiddingId(requestDto.getBidding_id());

        order.updateState(State.DESIGNATING_SHIPMENT_LOCATION);
        List<Bidding> biddings = order.getBiddings();
        for(Bidding b : biddings)
        {
            if(b.getId()!=requestDto.getBidding_id())
                b.updateStatus(BiddingStatus.FAILED);           //선택 비딩 제외 모두 fail 설정
        }

        bidding.updateStatus(BiddingStatus.SUCCESS);


        Contract contract = Contract.builder()
                .detail(bidding.getDetail())
                .order(order)
                .bidding(bidding)
                .shipmentLocation(bidding.getCompany().getCompanyInfo().getAddress() + " " + bidding.getCompany().getCompanyInfo().getDetailAddress())
                .state(State.DESIGNATING_SHIPMENT_LOCATION)
                .build();

        ContractResponseDto responseDto = new ContractResponseDto(contractService.registerContract(contract));

        try {
            firebaseCloudMessageService.sendMessageTo(bidding.getCompany().getFcmToken(), "낙찰", "낙찰","110");
        }
        catch (IOException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);


    }

    @GetMapping("/api/contract")            //특정 company의 성사계약 목록 조회
    public ResponseEntity<ApiResponse<List<ContractResponseDto>>> getContract4Company(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        List<Bidding> biddings = biddingService.getAllBiddingsInSuccessAndCompany(company);

        List<ContractResponseDto> responseDtos = new ArrayList<>();

        for (Bidding b : biddings)
        {
            ContractResponseDto responseDto = new ContractResponseDto(contractService.getContractByBidding(b));
            responseDtos.add(responseDto);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @GetMapping("/api/contract/3/{order_id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>> getShipmentLocation4User(@PathVariable("order_id") Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        Contract contract = contractService.getContractByOrder(order);

        Map<String, Object> map = new HashMap<>();
        map.put("shipment_location",contract.getShipmentLocation());
        map.put("company_name",contract.getBidding().getCompany().getName());
        map.put("contract_id",contract.getId());

        return new ResponseEntity<>(ApiResponse.response(
            HttpStatusCode.OK,
            HttpResponseMsg.GET_SUCCESS,
            map), HttpStatus.OK);
    }

    @PutMapping("/api/contract/3/{order_id}")               //state 3->4   **알림필요
    public ResponseEntity<ApiResponse<Map<String,Object>>> finishChangeShipmentLocation(@PathVariable("order_id") Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        Contract contract = contractService.getContractByOrder(order);
        Map<String,Object> map = new HashMap<>();

        contract.updateState(State.CAR_EXAMINATION);
        contractService.registerContract(contract);
        order.updateState(State.CAR_EXAMINATION);
        orderService.saveOrder(order);

        try {
            firebaseCloudMessageService.sendMessageTo(contract.getBidding().getCompany().getFcmToken(), "출고지 설정 완료", "출고지 설정 완료.","112");
        }
        catch (IOException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }


        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                map), HttpStatus.OK);

    }

    @PostMapping("/api/contract/4/{contract_id}")                 //차량검수사진 업로드.
    @Transactional
    public ResponseEntity<ApiResponse<ContractInspectionImageResponseDto>> uploadInspectionImages(@RequestParam("files") List<MultipartFile> files,@PathVariable("contract_id") Long contractId)
    {
        List<String> urls = new ArrayList<>();
        Contract contract = contractService.getContractById(contractId);

        for(MultipartFile file : files)
        {
            String url = fileUploadService.uploadImage(file);
            urls.add(url);
            InspectionImageUrl inspectionImageUrl = InspectionImageUrl.builder()
                    .imageUrl(url)
                    .build();
            contractService.registerInspectionImageUrl(contract,inspectionImageUrl);
        }
        contractService.registerContract(contract);
        ContractInspectionImageResponseDto responseDto = new ContractInspectionImageResponseDto(contract);
        try {
            firebaseCloudMessageService.sendMessageTo(contract.getOrder().getUser().getFcmToken(), "차량 검수 사진 등록", "차량 검수 사진 등록됨.","210");
        }
        catch (IOException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.OK);

    }

    @GetMapping("/api/contract/4/{contract_id}")
    public ResponseEntity<ApiResponse<ContractInspectionImageResponseDto>> getInspectionImageUrls(@PathVariable("contract_id") Long contractId)
    {
        Contract contract = contractService.getContractById(contractId);

        ContractInspectionImageResponseDto responseDto = new ContractInspectionImageResponseDto(contract);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @PutMapping("/api/contract/4")           // state 4->5   **알림필요
    public ResponseEntity<ApiResponse> finishCarExamination(@RequestBody ContractRequestDto requestDto )
    {
        Contract contract = contractService.getContractById(requestDto.getId());

        Order order = contract.getOrder();

        order.updateState(State.CAR_EXAMINATION_FIN);

        contract.updateState(State.CAR_EXAMINATION_FIN);

        orderService.saveOrder(order);
        contractService.registerContract(contract);

        try {
            firebaseCloudMessageService.sendMessageTo(order.getUser().getFcmToken(), "차량 검수 완료", "차량 검수 완료","211");
        }
        catch (IOException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("/api/contract/5/{order_id}")               //state 5->6  **알림필요.
    public ResponseEntity<ApiResponse> confirmExamnation(@PathVariable("order_id") Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        Contract contract = contractService.getContractByOrder(order);

        contract.updateState(State.CONSTRUCTING);
        contractService.registerContract(contract);
        order.updateState(State.CONSTRUCTING);
        orderService.saveOrder(order);

        try {
            firebaseCloudMessageService.sendMessageTo(contract.getBidding().getCompany().getFcmToken(), "차량 인수 결정 완료", "차량 인수 결정 완료.","113");
        }
        catch (IOException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS
                ), HttpStatus.OK);
    }

    //TODO:시공중 사진 등록, 사진 등록시 알림 보내기(212)

    @PostMapping("/api/contract/6/{contract_id}")                 //차량검수사진 업로드.
    @Transactional
    public ResponseEntity<ApiResponse<ContractConstructionImageResponseDto>> uploadConstructionImages(@RequestParam("files") List<MultipartFile> files, @PathVariable("contract_id") Long contractId)
    {
        List<String> urls = new ArrayList<>();
        Contract contract = contractService.getContractById(contractId);

        for(MultipartFile file : files)
        {
            String url = fileUploadService.uploadImage(file);
            urls.add(url);
            ConstructionImageUrl constructionImageUrl = ConstructionImageUrl.builder()
                    .imageUrl(url)
                    .build();
            contractService.registerConstructionImageUrl(contract,constructionImageUrl);
        }
        contractService.registerContract(contract);
        ContractConstructionImageResponseDto responseDto = new ContractConstructionImageResponseDto(contract);
        try {
            firebaseCloudMessageService.sendMessageTo(contract.getOrder().getUser().getFcmToken(), "차량 시공 사진 등록", "차량 시공 사진 등록됨.","212");
        }
        catch (IOException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.OK);

    }

    @GetMapping("/api/contract/6/{contract_id}")
    public ResponseEntity<ApiResponse<ContractConstructionImageResponseDto>> getConstructionImageUrls(@PathVariable("contract_id") Long contractId)
    {
        Contract contract = contractService.getContractById(contractId);

        ContractConstructionImageResponseDto responseDto = new ContractConstructionImageResponseDto(contract);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @PutMapping("api/contract/6")           //state 6->7 **알림필요
    public ResponseEntity<ApiResponse> finishConstruction(@RequestBody ContractRequestDto requestDto)
    {
        Contract contract = contractService.getContractById(requestDto.getId());

        Order order = contract.getOrder();

        order.updateState(State.CONSTRUCTION_COMPLETED);

        contract.updateState(State.CONSTRUCTION_COMPLETED);

        orderService.saveOrder(order);
        contractService.registerContract(contract);

        try {
            firebaseCloudMessageService.sendMessageTo(order.getUser().getFcmToken(), "시공 완료", "시공 완료","213");
        }
        catch (IOException e)
        {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS), HttpStatus.OK);

    }

}
