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
    public List<CompanyResponseDto> getCompaniesByName(String company_name)
    {
        List<Company> companies = companyRepository.findAllByName(company_name)
                .orElseThrow(()->new IllegalArgumentException());

        List<CompanyResponseDto> companyResponseDtos = new ArrayList<>();
        for(Company c : companies)
        {
            companyResponseDtos.add(new CompanyResponseDto(c));
        }

        return companyResponseDtos;
    }

}
