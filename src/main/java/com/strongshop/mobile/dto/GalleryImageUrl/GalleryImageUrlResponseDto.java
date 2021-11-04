package com.strongshop.mobile.dto.GalleryImageUrl;

import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GalleryImageUrlResponseDto {

    private Long id;
    private Long galleryId;
    private String imageUrl;

    public GalleryImageUrlResponseDto (GalleryImageUrl galleryImageUrl)
    {
        this.id = galleryImageUrl.getId();
        this.galleryId = galleryImageUrl.getGallery().getId();
        this.imageUrl = galleryImageUrl.getImageUrl();
    }
}
