package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.dto.Bidding.BiddingResponseDto;
import com.strongshop.mobile.dto.Contract.ContractRequestDto;
import com.strongshop.mobile.dto.Contract.ContractResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.BiddingService;
import com.strongshop.mobile.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContractController {

    private final OrderService orderService;
    private final BiddingService biddingService;


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
        bidding.updateStatus(BiddingStatus.SUCCESS);        //선택비딩 상태 성공으로 설정.

        Contract contract = Contract.builder()
                .detail(bidding.getDetail())
                .order(order)
                .bidding(bidding)
                .state(State.DESIGNATING_SHIPMENT_LOCATION)
                .build();

        ContractResponseDto responseDto = new ContractResponseDto(contract);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);


    }

}
