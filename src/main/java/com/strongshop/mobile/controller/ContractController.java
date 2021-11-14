package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.dto.Contract.ContractRequestDto;
import com.strongshop.mobile.dto.Contract.ContractResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.BiddingService;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.ContractService;
import com.strongshop.mobile.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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

        return new ResponseEntity<>(ApiResponse.response(
            HttpStatusCode.OK,
            HttpResponseMsg.GET_SUCCESS,
            map), HttpStatus.OK);
    }

    @PutMapping("/api/contract/3/{order_id}")               //state 3->4
    public ResponseEntity<ApiResponse> finishChangeShipmentLocation(@PathVariable("order_id") Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        Contract contract = contractService.getContractByOrder(order);

        contract.updateState(State.CAR_EXAMINATION);
        contractService.registerContract(contract);
        order.updateState(State.CAR_EXAMINATION);
        orderService.saveOrder(order);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS), HttpStatus.OK);

    }

//    @PostMapping("/api/contract/4")
//    public ResponseEntity<ApiResponse<Map<String,Object>>> uploadInspectionImages(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request)
//    {
//        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
//        Company company = companyService.getCompanyByEmail(email);
//
//    }         검수 사진 등록.


    @PutMapping("/api/contract/4")           // state 4->5
    public ResponseEntity<ApiResponse> finishCarExamination(@RequestBody ContractRequestDto requestDto )
    {
        Contract contract = contractService.getContractById(requestDto.getId());

        Order order = contract.getOrder();

        order.updateState(State.CAR_EXAMINATION_FIN);

        contract.updateState(State.CAR_EXAMINATION_FIN);

        orderService.saveOrder(order);
        contractService.registerContract(contract);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("/api/contract/5/{order_id}")               //state 5->6
    public ResponseEntity<ApiResponse> confirmExamnation(@PathVariable("order_id") Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        Contract contract = contractService.getContractByOrder(order);

        contract.updateState(State.CONSTRUCTING);
        contractService.registerContract(contract);
        order.updateState(State.CONSTRUCTING);
        orderService.saveOrder(order);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("api/contract/6")           //state 6->7
    public ResponseEntity<ApiResponse> finishConstruction(@RequestBody Long contract_id)
    {
        Contract contract = contractService.getContractById(contract_id);

        Order order = contract.getOrder();

        order.updateState(State.CONTSTRUCTION_COMPLETED);

        contract.updateState(State.CONTSTRUCTION_COMPLETED);

        orderService.saveOrder(order);
        contractService.registerContract(contract);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS), HttpStatus.OK);

    }

}
