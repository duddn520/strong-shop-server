package com.strongshop.mobile.service.Company;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyInfo;
import com.strongshop.mobile.domain.Company.CompanyInfoRepository;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.dto.Company.CompanyInfoRequestDto;
import com.strongshop.mobile.dto.Company.CompanyInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CompanyInfoService {

    private final CompanyInfoRepository companyInfoRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyInfoResponseDto registerCompanyInfo(CompanyInfoRequestDto requestDto)
    {

        Company company = companyRepository.findById(requestDto.getCompany_id())
                .orElseThrow(()-> new IllegalArgumentException());

        CompanyInfo companyInfo = requestDto.toEntity();
        companyInfo.updateCompany(company);

        return new CompanyInfoResponseDto(companyInfoRepository.save(companyInfo));
    }

    @Transactional
    public CompanyInfoResponseDto updateCompanyInfo(CompanyInfoRequestDto requestDto)
    {
        CompanyInfo companyInfo = companyInfoRepository.findByCompanyId(requestDto.getCompany_id())
                .orElseThrow(()-> new IllegalArgumentException());

        companyInfo.updateCompanyInfo(requestDto.toEntity());
        return new CompanyInfoResponseDto(companyInfo);
    }

    @Transactional
    public CompanyInfo getCompanyInfo(Long companyId){
        CompanyInfo companyInfo = companyInfoRepository.findByCompanyId(companyId)
                .orElseGet(()->new CompanyInfo());

        return companyInfo;
    }
}