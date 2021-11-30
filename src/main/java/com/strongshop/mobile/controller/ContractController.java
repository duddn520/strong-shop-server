package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingHistory;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Contract.ReviewStatus;
import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.Contract.*;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.*;
import com.strongshop.mobile.service.Company.CompanyService;
import lombok.RequiredArgsConstructor;
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
    private final CompletedContractService completedContractService;
    private final UserService userService;

    //TODO orderId와 biddingId를 받아서 contract생성, order의 상태, contract상태 변경 필요. Bidding상태도 변경.(POST)
    @PostMapping("/api/contract")
    @Transactional
    public ResponseEntity<ApiResponse<ContractResponseDto>> registerContract(@RequestBody ContractRequestDto requestDto)
    {

        Order order = orderService.getOrderByOrderId(requestDto.getOrder_id());
        Bidding bidding = biddingService.getBiddingByBiddingId(requestDto.getBidding_id());
        Company succompany = bidding.getCompany();
        order.updateState(State.DESIGNATING_SHIPMENT_LOCATION);
        List<Bidding> biddings = order.getBiddings();

        for(Bidding b : biddings)
        {
            Company company = b.getCompany();

            if(b.getId()!=requestDto.getBidding_id()) {
                b.updateStatus(BiddingStatus.FAILED);//선택 비딩 제외 모두 fail 설정
                BiddingHistory biddingHistory = BiddingHistory.builder()
                        .company(company)
                        .details(b.getDetail())
                        .createdTime(order.getCreatedTime())
                        .biddingStatus(BiddingStatus.FAILED)
                        .build();

                company.getBiddingHistories().add(biddingHistory);

                try {
                    firebaseCloudMessageService.sendMessageTo(b.getCompany().getFcmToken(), "낙찰 실패", "낙찰 실패", "111");
                }
                catch (IOException e)
                {
                    return new ResponseEntity<>(ApiResponse.response(
                            HttpStatusCode.FORBIDDEN,
                            HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
                }
            }
        }

        bidding.updateStatus(BiddingStatus.SUCCESS);
        BiddingHistory successhistory = BiddingHistory.builder()
                .company(succompany)
                .details(bidding.getDetail())
                .createdTime(order.getCreatedTime())
                .biddingStatus(BiddingStatus.SUCCESS)
                .build();

        succompany.getBiddingHistories().add(successhistory);

        Contract contract = Contract.builder()
                .detail(bidding.getDetail())
                .order(order)
                .bidding(bidding)
                .shipmentLocation(bidding.getCompany().getCompanyInfo().getAddress() + " " + bidding.getCompany().getCompanyInfo().getDetailAddress())
                .state(State.DESIGNATING_SHIPMENT_LOCATION)
                .build();

        ContractResponseDto responseDto = new ContractResponseDto(contractService.registerContract(contract));

        try {
            firebaseCloudMessageService.sendMessageTo(bidding.getCompany().getFcmToken(), "낙찰", "낙찰","110",order.getUser().getNickname());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
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
        map.put("company_id",contract.getBidding().getCompany().getId());
        map.put("receipt",contract.getBidding().getDetail());

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
            firebaseCloudMessageService.sendMessageTo(contract.getBidding().getCompany().getFcmToken(), "출고지 설정 완료", "출고지 설정 완료","112",order.getUser().getNickname());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
        }


        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                map), HttpStatus.OK);

    }

    @PostMapping(value = "/api/contract/4/{contract_id}",headers = ("content-type=multipart/*"))                 //차량검수사진 업로드.
    @Transactional
    public ResponseEntity<ApiResponse<ContractInspectionImageResponseDto>> uploadInspectionImages(@RequestParam("files") List<MultipartFile> files,@PathVariable("contract_id") Long contractId)
    {
        Contract contract = contractService.getContractById(contractId);
        Company company = companyService.getCompanyById(contract.getCompanyId());
        for(MultipartFile file : files)
        {
            String filename = fileUploadService.uploadImage(file);
            String url = fileUploadService.getFileUrl(filename);
            InspectionImageUrl imageUrl = InspectionImageUrl.builder()
                    .imageUrl(url)
                    .filename(filename)
                    .contract(contract)
                    .build();
            contract.getInspectionImageUrls().add(imageUrl);
        }
        ContractInspectionImageResponseDto responseDto = new ContractInspectionImageResponseDto(contract);
        try {
            firebaseCloudMessageService.sendMessageTo(contract.getOrder().getUser().getFcmToken(), "차량 검수 사진 등록", "차량 검수 사진 등록","210",company.getName());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.OK);

    }

    @GetMapping("/api/contract/4/{contract_id}")
    @Transactional
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
        Company company = companyService.getCompanyById(contract.getCompanyId());

        Order order = contract.getOrder();

        order.updateState(State.CAR_EXAMINATION_FIN);

        contract.updateState(State.CAR_EXAMINATION_FIN);

        orderService.saveOrder(order);
        contractService.registerContract(contract);

        try {
            firebaseCloudMessageService.sendMessageTo(order.getUser().getFcmToken(), "차량 검수 완료", "차량 검수 완료","211",company.getName());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
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
            firebaseCloudMessageService.sendMessageTo(contract.getBidding().getCompany().getFcmToken(), "차량 인수 결정 완료", "차량 인수 결정 완료","113",order.getUser().getNickname());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS
                ), HttpStatus.OK);
    }

    //TODO:시공중 사진 등록, 사진 등록시 알림 보내기(212)

    @PostMapping(value = "/api/contract/6/{contract_id}" ,headers = ("content-type=multipart/*"))                 //차량검수사진 업로드.
    @Transactional
    public ResponseEntity<ApiResponse<ContractConstructionImageResponseDto>> uploadConstructionImages(@RequestParam("files") List<MultipartFile> files, @PathVariable("contract_id") Long contractId)
    {
        Contract contract = contractService.getContractById(contractId);
        Company company = companyService.getCompanyById(contract.getCompanyId());
        for(MultipartFile file : files)
        {
            String filename = fileUploadService.uploadImage(file);
            String url = fileUploadService.getFileUrl(filename);
            ConstructionImageUrl imageUrl = ConstructionImageUrl.builder()
                    .imageUrl(url)
                    .filename(filename)
                    .contract(contract)
                    .build();
            contract.getConstructionImageUrls().add(imageUrl);
        }
        ContractConstructionImageResponseDto responseDto = new ContractConstructionImageResponseDto(contract);
        try {
            firebaseCloudMessageService.sendMessageTo(contract.getOrder().getUser().getFcmToken(), "차량 시공 사진 등록", "차량 시공 사진 등록","212",company.getName());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.OK);

    }

    @GetMapping("/api/contract/6/{contract_id}")
    @Transactional
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
        Company company = companyService.getCompanyById(contract.getCompanyId());

        Order order = contract.getOrder();

        order.updateState(State.CONSTRUCTION_COMPLETED);

        contract.updateState(State.CONSTRUCTION_COMPLETED);

        orderService.saveOrder(order);
        contractService.registerContract(contract);

        try {
            firebaseCloudMessageService.sendMessageTo(order.getUser().getFcmToken(), "시공 완료", "시공 완료","213",company.getName());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS), HttpStatus.OK);

    }

    @PutMapping("api/contract/7/{contract_id}")
    @Transactional
    public ResponseEntity<ApiResponse<CompletedContractResponseDto>> finishContract(@PathVariable("contract_id") Long contractId, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);
        Contract contract = contractService.getContractById(contractId);

        Order order = contract.getOrder();
        Bidding bidding = contract.getBidding();
        Company company = bidding.getCompany();
        CompletedContract completedContract = CompletedContract.builder()
                .user(order.getUser())
                .company(bidding.getCompany())
                .details(contract.getDetail())
                .shipmentLocation(contract.getShipmentLocation())
                .reviewStatus(ReviewStatus.NOT_WRITTEN)
                .build();
        user.getCompletedContracts().add(completedContract);
        company.getCompletedContracts().add(completedContract);

        completedContractService.registerCompletedContract(completedContract);

        List<InspectionImageUrl> inspectionImageUrls = contract.getInspectionImageUrls();
        List<ConstructionImageUrl> constructionImageUrls = contract.getConstructionImageUrls();

        for(InspectionImageUrl i : inspectionImageUrls)
        {
            fileUploadService.removeFile(i.getFilename());
        }

        for(ConstructionImageUrl c : constructionImageUrls)
        {
            fileUploadService.removeFile(c.getFilename());
        }

        try {
            firebaseCloudMessageService.sendMessageTo(order.getUser().getFcmToken(), "출고 완료", "차량 출고 완료","114",order.getUser().getNickname());
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
        }

        contractService.deleteContract(contract);
        orderService.deleteOrder(order);
        biddingService.deleteBidding(bidding);

        CompletedContractResponseDto responseDto = new CompletedContractResponseDto(completedContract);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS,
                responseDto), HttpStatus.OK);
    }

}
