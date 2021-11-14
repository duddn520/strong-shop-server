package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyInfo;
import com.strongshop.mobile.dto.Company.CompanyInfoRequestDto;
import com.strongshop.mobile.dto.Company.CompanyInfoResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyInfoService;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CompanyInfoController {

    private final CompanyInfoService companyInfoService;
    private final CompanyService companyService;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileUploadService fileUploadService;

    @PostMapping("/api/companyinfo")
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> registerCompanyInfo( @RequestBody CompanyInfoRequestDto requestDto, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        requestDto.setCompany_id(company.getId());
        CompanyInfoResponseDto responseDto = companyInfoService.registerCompanyInfo(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @PutMapping("/api/companyinfo")
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> updateCompanyInfo(@RequestBody CompanyInfoRequestDto requestDto,HttpServletRequest request)
    {
        String email  = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        requestDto.setCompany_id(company.getId());
        CompanyInfoResponseDto responseDto = companyInfoService.updateCompanyInfo(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @GetMapping("/api/companyinfo")
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> getCompanyInfo(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        Long company_id = company.getId();

        CompanyInfo companyInfo = companyInfoService.getCompanyInfo(company_id);
        if(companyInfo.getId()==null)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.NO_CONTENT,
                    HttpResponseMsg.NO_CONTENT), HttpStatus.NO_CONTENT);

        }
        else {
            CompanyInfoResponseDto responseDto = new CompanyInfoResponseDto(companyInfo);
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.GET_SUCCESS,
                    responseDto), HttpStatus.OK);
        }

    }

    @GetMapping("/api/companyinfo/{company_id}")            //유저가 요청하는 get요청, conmpnayId를 인자로 받는다.
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> getCompanyInfo4User(@PathVariable("company_id") Long companyId)
    {
        CompanyInfo companyInfo = companyInfoService.getCompanyInfo(companyId);
        if(companyInfo.getId()==null)
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.NO_CONTENT,
                    HttpResponseMsg.NO_CONTENT), HttpStatus.NO_CONTENT);

        }
        else {
            CompanyInfoResponseDto responseDto = new CompanyInfoResponseDto(companyInfo);
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.GET_SUCCESS,
                    responseDto), HttpStatus.OK);
        }

    }

    @PostMapping("/api/companyinfo/bgi")
    public ResponseEntity<ApiResponse<Map<String,Object>>> registerBackgroundImage(@RequestParam("file") MultipartFile file, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        CompanyInfo companyInfo = company.getCompanyInfo();

        String url = fileUploadService.uploadImage(file);

        companyInfo.updateBackgroundImageUrl(url);

        Map<String,Object> map = new HashMap<>();
        map.put("url",url);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS,
                map), HttpStatus.OK);

    }
}
