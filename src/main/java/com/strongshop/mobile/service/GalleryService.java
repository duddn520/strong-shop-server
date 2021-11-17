package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Gallery.GalleryRepository;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.domain.Image.GalleryImageUrlRepository;
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
    private final GalleryImageUrlRepository galleryImageUrlRepository;

    @Transactional
    public Gallery registerGallery(Gallery gallery) {

        return galleryRepository.saveAndFlush(gallery);
    }

    @Transactional
    public List<Gallery> getAllGalleriesByCompanyId(Long companyId){
        List<Gallery> galleries = galleryRepository.findAllByCompanyIdOrderByCreatedTimeAsc(companyId)
                .orElseThrow(()->new RuntimeException("갤러리가 존재하지 않습니다."));
        return galleries;
    }
}
