package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyInfo;
import com.strongshop.mobile.domain.Company.CompanyInfoRepository;
import com.strongshop.mobile.dto.Company.CompanyInfoRequestDto;
import com.strongshop.mobile.dto.Company.CompanyInfoResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CompanyInfoController {

    private final CompanyService companyService;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileUploadService fileUploadService;
    private final CompanyInfoRepository companyInfoRepository;

    @PostMapping("/api/companyinfo")
    @Transactional
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> registerCompanyInfo(@RequestBody CompanyInfoRequestDto requestDto, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        CompanyInfo companyInfo = CompanyInfo.builder()
                .company(company)
                .address(requestDto.getAddress())
                .contact(requestDto.getContact())
                .blogUrl(requestDto.getBlogUrl())
                .detailAddress(requestDto.getDetailAddress())
                .introduction(requestDto.getIntroduction())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .siteUrl(requestDto.getSiteUrl())
                .snsUrl(requestDto.getSnsUrl())
                .build();

        company.updateCompanyInfo(companyInfo);
        CompanyInfoResponseDto responseDto = new CompanyInfoResponseDto(companyInfo);

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

        CompanyInfo companyInfo = CompanyInfo.builder()
                .company(company)
                .address(requestDto.getAddress())
                .contact(requestDto.getContact())
                .backgroundImageUrl(requestDto.getBackgroundImageUrl())
                .blogUrl(requestDto.getBlogUrl())
                .detailAddress(requestDto.getDetailAddress())
                .introduction(requestDto.getIntroduction())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .siteUrl(requestDto.getSiteUrl())
                .snsUrl(requestDto.getSnsUrl())
                .build();

        company.updateCompanyInfo(companyInfo);
        CompanyInfoResponseDto responseDto = new CompanyInfoResponseDto(companyInfo);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @GetMapping("/api/companyinfo")
    public ResponseEntity<ApiResponse<CompanyInfoResponseDto>> getCompanyInfo(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        CompanyInfo companyInfo = company.getCompanyInfo();
        if(companyInfo.getId()==null)
        {
            log.debug("companyId: {} have no companyInfo Entity. (CompanyInfoController.getCompanyInfo)",company.getId());
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
        Company company = companyService.getCompanyById(companyId);
        CompanyInfo companyInfo = company.getCompanyInfo();
        if(companyInfo.getId()==null)
        {
            log.debug("companyId: {} have no companyInfo Entity. (CompanyInfoController.getCompanyInfo4User)",company.getId());
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

        if(companyInfo.getBackgroundImageUrl()!=null)
        {
            String pastfilename = companyInfo.getBackgroundFilename();
            fileUploadService.removeFile(pastfilename);
        }

        String filename = fileUploadService.uploadImage(file);
        String url = fileUploadService.getFileUrl(filename);

        companyInfo.updateBackgroundImageUrl(url, filename);

        Map<String,Object> map = new HashMap<>();
        map.put("url",url);
        companyInfoRepository.save(companyInfo);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS,
                map), HttpStatus.OK);

    }
}
