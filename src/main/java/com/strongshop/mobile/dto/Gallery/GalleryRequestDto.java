package com.strongshop.mobile.dto.Gallery;

import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class GalleryRequestDto {

    private Long id;
    private Long company_id;
    private String content;
    private List<GalleryImageUrl> galleryImageUrls = new ArrayList<>();



    public Gallery toEntity(){
        return Gallery.builder()
                .companyId(company_id)
                .content(content)
                .imageUrls(galleryImageUrls)
                .build();
    }
}
