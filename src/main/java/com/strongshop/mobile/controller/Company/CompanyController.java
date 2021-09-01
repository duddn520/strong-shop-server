package com.strongshop.mobile.controller.Company;

import com.strongshop.mobile.dto.Company.CompanyRequestDto;
import com.strongshop.mobile.dto.Company.CompanyResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/api/company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> updateCompany(@RequestBody CompanyRequestDto requestDto){

        CompanyResponseDto responseDto = companyService.updateCompany(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @GetMapping("/api/company")
    public ResponseEntity<ApiResponse<List<CompanyResponseDto>>> getCompany(@RequestParam("company_name") String company_name){

        List<CompanyResponseDto> responseDtos = companyService.getCompaniesByName(company_name);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }
}
