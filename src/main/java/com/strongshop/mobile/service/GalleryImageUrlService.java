package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Gallary.GallaryRepository;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.domain.Image.GalleryImageUrlRepository;
import com.strongshop.mobile.dto.GalleryImageUrl.GalleryImageUrlRequestDto;
import com.strongshop.mobile.dto.GalleryImageUrl.GalleryImageUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GalleryImageUrlService {

    private final GalleryImageUrlRepository galleryImageUrlRepository;
    private final GallaryRepository gallaryRepository;

    @Transactional
    public List<GalleryImageUrl> registerGalleryImageUrl(List<String> imageUrls, Long gallaryId){
        GalleryImageUrlRequestDto requestDto = new GalleryImageUrlRequestDto();
        List<GalleryImageUrl> galleryImageUrls = new ArrayList<>();
        requestDto.setGallaryId(gallaryId);
        for(String i : imageUrls){
            requestDto.setImageUrl(i);
            galleryImageUrlRepository.save(requestDto.toEntity());
            galleryImageUrls.add(requestDto.toEntity());
        }
        return galleryImageUrls;
    }
}
