package com.strongshop.mobile.controller.Company;

import com.strongshop.mobile.dto.Company.CompanyInfoRequestDto;
import com.strongshop.mobile.dto.Company.CompanyInfoResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CompanyInfoController {

    private final CompanyInfoService companyInfoService;

    @PostMapping("/api/companyinfo")
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> registerCompanyInfo(@RequestBody CompanyInfoRequestDto requestDto)
    {
        CompanyInfoResponseDto responseDto = companyInfoService.registerCompanyInfo(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @PutMapping("/api/companyinfo")
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> updateCompanyInfo(@RequestBody CompanyInfoRequestDto requestDto)
    {
        CompanyInfoResponseDto responseDto = companyInfoService.updateCompanyInfo(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @GetMapping("/api/companyinfo")
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> getCompanyInfo(@RequestParam("company_id") Long company_id)
    {
        CompanyInfoResponseDto responseDto = companyInfoService.getCompanyInfo(company_id);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDto), HttpStatus.OK);
    }
}
