package com.strongshop.mobile.service.Company;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.dto.Company.CompanyRequestDto;
import com.strongshop.mobile.dto.Company.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyResponseDto registerCompany(CompanyRequestDto requestDto) {
        return new CompanyResponseDto(companyRepository.save(requestDto.toEntity()));
    }

    @Transactional
    public CompanyResponseDto updateCompany(CompanyRequestDto requestDto){
        System.out.println("requestDto.getId() = " + requestDto.getId());

        Company company = companyRepository.findById(requestDto.getId())
                .orElseThrow(()-> new IllegalArgumentException());

        return new CompanyResponseDto(company.updateCompany(requestDto.toEntity()));
    }

    @Transactional
    public Company getCompanyByEmail(String email){
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException());

        return company;
    }
    @Transactional
    public void deleteCompany(Company company)
    {
        companyRepository.delete(company);
    }

}
