package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.dto.Bidding.BiddingRequestDto;
import com.strongshop.mobile.dto.Bidding.BiddingResponseDto;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.BiddingService;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BiddingController {

    private final BiddingService biddingService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyService companyService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final OrderService orderService;

    //입찰 등록
    @PostMapping("/api/bidding")
    @Transactional
    public ResponseEntity<ApiResponse<BiddingResponseDto>> registerBidding(@RequestBody BiddingRequestDto requestDto, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        Order order = orderService.getOrderByOrderId(requestDto.getOrder_id());

        if(!order.getState().equals(State.BIDDING))
        {
            return new ResponseEntity<>(ApiResponse.response(       //입찰 넣는 도중 취소되거나 낙찰된 경우. 거부된 response 전송.
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
        }
        else {

            Bidding bidding = biddingService.registerBidding(requestDto,order, company);


            try {
                firebaseCloudMessageService.sendMessageTo(order.getUser().getFcmToken(), "새로운 입찰이 있습니다.", "새로운 입찰이 있습니다.", "200");
            } catch (IOException e) {
                System.out.println("e.getMessage() = " + e.getMessage());
            }
            BiddingResponseDto responseDto = new BiddingResponseDto(bidding);

            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.CREATED,
                    HttpResponseMsg.POST_SUCCESS,
                    responseDto), HttpStatus.CREATED);
        }
    }

    @GetMapping("/api/bidding/{order_id}")
    public ResponseEntity<ApiResponse<List<BiddingResponseDto>>> getAllBiddings4User(@PathVariable("order_id") Long orderId)
    {
        List<Bidding> biddings = biddingService.getAllBiddingsByOrderId(orderId);

        List<BiddingResponseDto> responseDtos = new ArrayList<>();
        for(Bidding b : biddings)
        {
            BiddingResponseDto responseDto = new BiddingResponseDto(b);
            responseDtos.add(responseDto);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @GetMapping("/api/bidding")             //업체의 입찰목록 조회
    public ResponseEntity<ApiResponse<List<BiddingResponseDto>>> getMyBiddings(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        List<Bidding> biddings = biddingService.getAllBiddingsByCompany(company);
        List<BiddingResponseDto> responseDtos = new ArrayList<>();

        for(Bidding b : biddings)
        {
            if(b.getStatus().equals(BiddingStatus.ONGOING)) {
                BiddingResponseDto responseDto = new BiddingResponseDto(b);
                responseDtos.add(responseDto);
            }
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/api/bidding/{bidding_id}")
    @Transactional
    public ResponseEntity<ApiResponse> removeBidding(@PathVariable("bidding_id") Long biddingId)
    {
        Bidding bidding = biddingService.getBiddingByBiddingId(biddingId);
        biddingService.deleteBidding(bidding);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS), HttpStatus.OK);

    }

}
