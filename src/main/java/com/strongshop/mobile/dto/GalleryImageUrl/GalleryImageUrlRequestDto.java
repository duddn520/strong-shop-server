package com.strongshop.mobile.dto.GalleryImageUrl;

import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GalleryImageUrlRequestDto {

    private Long id;
    private Long gallaryId;
    private String imageUrl;

    public GalleryImageUrl toEntity(){
        return GalleryImageUrl.builder()
                .id(id)
                .gallaryId(gallaryId)
                .imageUrl(imageUrl)
                .build();
    }
}
