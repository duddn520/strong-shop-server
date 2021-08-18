package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.dto.Company.CompanyRequestDto;
import com.strongshop.mobile.dto.Company.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyResponseDto registerCompany(CompanyRequestDto requestDto) {
        return new CompanyResponseDto(companyRepository.save(requestDto.toEntity()));
    }
}
