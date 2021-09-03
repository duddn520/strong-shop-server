package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Gallary.GallaryImage;
import com.strongshop.mobile.domain.Gallary.GallaryRepository;
import com.strongshop.mobile.dto.Gallary.GallaryRequestDto;
import com.strongshop.mobile.dto.Gallary.GallaryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GallaryService {

    private final GallaryRepository gallaryRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public GallaryResponseDto registerGallary(GallaryRequestDto requestDto){
        Company company = companyRepository.findById(requestDto.getCompany_id())
                .orElseThrow(()->new IllegalArgumentException());

        Gallary gallary = requestDto.toEntity();
        gallary.updateCompany(company);

        return new GallaryResponseDto(gallary);
    }

    @Transactional
    public List<Gallary> findGallariesByCompany(Company company)
    {
        List<Gallary> gallaries = new ArrayList<>();
        gallaries = gallaryRepository.findAllByCompanyIdOrderByCreatedTimeDesc(company.getId());

        return gallaries;
    }
}
