package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Gallery.GalleryRepository;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.domain.Image.GalleryImageUrlRepository;
import com.strongshop.mobile.dto.GalleryImageUrl.GalleryImageUrlRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GalleryImageUrlService {

    private final GalleryImageUrlRepository galleryImageUrlRepository;
    private final GalleryRepository galleryRepository;

    @Transactional
    public List<GalleryImageUrl> registerGalleryImageUrl(List<String> imageUrls, Long galleryId){
        GalleryImageUrlRequestDto requestDto = new GalleryImageUrlRequestDto();
        List<GalleryImageUrl> galleryImageUrls = new ArrayList<>();
        requestDto.setGalleryId(galleryId);
        for(String i : imageUrls){
            requestDto.setImageUrl(i);
            GalleryImageUrl imgUrl = requestDto.toEntity();
            galleryImageUrls.add(imgUrl);
        }
        return galleryImageUrls;
    }
}
