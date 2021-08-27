package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.dto.Bidding.BiddingRequestDto;
import com.strongshop.mobile.dto.Bidding.BiddingResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.BiddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BiddingController {

    private final BiddingService biddingService;

    //입찰 등록
    @PostMapping("/api/bidding")
    public ResponseEntity<ApiResponse<BiddingResponseDto>> registerBidding(@RequestBody BiddingRequestDto requestDto)
    {
        BiddingResponseDto responseDto = biddingService.registerBidding(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);

    }

    //입찰 수정
    @PutMapping("/api/bidding")
    public ResponseEntity<ApiResponse<BiddingResponseDto>> updateBidding(@RequestBody BiddingRequestDto requestDto)
    {
        BiddingResponseDto responseDto = biddingService.updateBidding(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_BIDDING,
                responseDto), HttpStatus.OK);
    }

    //입찰 조회
    @GetMapping("/api/bidding")
    public ResponseEntity<ApiResponse<List<BiddingResponseDto>>> getBiddingsByCompany(@RequestParam("company_name") String companyName){

        List<BiddingResponseDto> responseDtos = biddingService.getBiddingsByCompany(companyName);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos),HttpStatus.OK);
    }
}
