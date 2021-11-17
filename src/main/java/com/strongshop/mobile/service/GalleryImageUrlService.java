package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Gallery.GalleryRepository;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.domain.Image.GalleryImageUrlRepository;
import com.strongshop.mobile.dto.Image.GalleryImageUrlRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GalleryImageUrlService {

    @Transactional
    public List<GalleryImageUrl> registerGalleryImageUrl(List<String> imageUrls){
        GalleryImageUrlRequestDto requestDto = new GalleryImageUrlRequestDto();
        List<GalleryImageUrl> galleryImageUrls = new ArrayList<>();
        for(String i : imageUrls){
            requestDto.setImageUrl(i);
            GalleryImageUrl imgUrl = requestDto.toEntity();
            galleryImageUrls.add(imgUrl);
        }
        return galleryImageUrls;
    }
}
