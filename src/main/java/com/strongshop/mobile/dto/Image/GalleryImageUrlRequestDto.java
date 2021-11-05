package com.strongshop.mobile.dto.Image;

import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GalleryImageUrlRequestDto {

    private Long id;
    private Long galleryId;
    private String imageUrl;

    public GalleryImageUrl toEntity(){
        return GalleryImageUrl.builder()
                .id(id)
                .imageUrl(imageUrl)
                .build();
    }
}
