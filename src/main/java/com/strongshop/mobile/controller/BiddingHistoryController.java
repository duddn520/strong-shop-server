package com.strongshop.mobile.controller;


import com.strongshop.mobile.domain.Bidding.BiddingHistory;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.dto.Bidding.BiddingHistoryResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BiddingHistoryController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyService companyService;

    @GetMapping("/api/biddinghistory")
    @Transactional
    public ResponseEntity<ApiResponse<List<BiddingHistoryResponseDto>>> getBiddingHistories(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        List<BiddingHistory> histories = company.getBiddingHistories();
        List<BiddingHistoryResponseDto> responseDtos = new ArrayList<>();

        for(BiddingHistory bh : histories)
        {
            BiddingHistoryResponseDto responseDto = new BiddingHistoryResponseDto(bh);
            responseDtos.add(responseDto);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);

    }
}
