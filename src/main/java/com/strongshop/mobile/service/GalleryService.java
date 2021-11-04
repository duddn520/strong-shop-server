package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Gallery.GalleryRepository;
import com.strongshop.mobile.dto.Gallery.GalleryRequestDto;
import com.strongshop.mobile.dto.Gallery.GalleryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GalleryService {

    private final GalleryRepository galleryRepository;

    @Transactional
    public Gallery registerGallery(GalleryRequestDto requestDto) {
        return galleryRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Gallery updateGallery(GalleryRequestDto requestDto, Long galleryId){
        Gallery gallery = galleryRepository.findById(galleryId)
                .orElseThrow(()->new RuntimeException("해당 갤러리는 존재하지 않습니다."));
        gallery.updateImageUrls(requestDto.getGalleryImageUrls());
        return gallery;
    }

    @Transactional
    public List<Gallery> findGallariesByCompany(Company company)
    {
        List<Gallery> gallaries = new ArrayList<>();
        gallaries = galleryRepository.findAllByCompanyIdOrderByCreatedTimeDesc(company.getId());

        return gallaries;
    }

    @Transactional
    public GalleryResponseDto refreshResponseDto(Long gallary_id)
    {
        Gallery gallery = galleryRepository.findById(gallary_id)
                .orElseThrow(()->new IllegalArgumentException());

        return new GalleryResponseDto(gallery);
    }
}
