package com.strongshop.mobile.service.Company;

import com.strongshop.mobile.domain.Company.Company;
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

    @Transactional
    public Company getCompanyById(Long companyId)
    {
        Company company =  companyRepository.findById(companyId)
                .orElseThrow(()->new RuntimeException("해당 업체가 존재하지 않습니다."));

        return company;
    }
    @Transactional
    public void removeFcmToken(Company company)
    {
        company.removeFcmToken();

        companyRepository.save(company);
    }
}
