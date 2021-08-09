package com.strongshop.mobile.controller;

import com.strongshop.mobile.dto.Company.CompanyRequestDto;
import com.strongshop.mobile.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public void registerCompany(@RequestBody CompanyRequestDto requestDto) {

        companyService.registerCompany(requestDto);




    }


}
