package com.strongshop.mobile.controller;

import com.strongshop.mobile.dto.Company.CompanyRequestDto;
import com.strongshop.mobile.dto.Company.CompanyResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/api/company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> registerCompany(@RequestBody CompanyRequestDto requestDto) {

        CompanyResponseDto responseDto = companyService.registerCompany(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }
}
