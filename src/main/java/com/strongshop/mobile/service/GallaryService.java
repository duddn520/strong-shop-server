package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Gallary.Gallary;
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

    @Transactional
    public Gallary registerGallary(GallaryRequestDto requestDto) {
        return gallaryRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Gallary updateGallary(GallaryRequestDto requestDto, Long gallaryId){
        Gallary gallary = gallaryRepository.findById(gallaryId)
                .orElseThrow(()->new RuntimeException("해당 갤러리는 존재하지 않습니다."));
        gallary.updateImageUrls(requestDto.getGalleryImageUrls());
        return gallary;
    }

    @Transactional
    public List<Gallary> findGallariesByCompany(Company company)
    {
        List<Gallary> gallaries = new ArrayList<>();
        gallaries = gallaryRepository.findAllByCompanyIdOrderByCreatedTimeDesc(company.getId());

        return gallaries;
    }

    @Transactional
    public GallaryResponseDto refreshResponseDto(Long gallary_id)
    {
        Gallary gallary = gallaryRepository.findById(gallary_id)
                .orElseThrow(()->new IllegalArgumentException());

        return new GallaryResponseDto(gallary);
    }
}
