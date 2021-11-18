package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Gallery.GalleryRepository;
import com.strongshop.mobile.domain.Image.GalleryImageUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final GalleryImageUrlRepository galleryImageUrlRepository;

    @Transactional
    public Gallery registerGallery(Gallery gallery) {

        return galleryRepository.save(gallery);
    }

    @Transactional
    public List<Gallery> getAllGalleriesByCompanyId(Long companyId){
        List<Gallery> galleries = galleryRepository.findAllByCompanyIdOrderByCreatedTimeAsc(companyId)
                .orElseThrow(()->new RuntimeException("갤러리가 존재하지 않습니다."));
        return galleries;
    }
}
