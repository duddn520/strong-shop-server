package com.strongshop.mobile.dto.Gallary;

import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class GallaryRequestDto {

    private Long id;
    private Long company_id;
    private String content;
    private List<GalleryImageUrl> galleryImageUrls = new ArrayList<>();



    public Gallary toEntity(){
        return Gallary.builder()
                .companyId(company_id)
                .content(content)
                .imageUrls(galleryImageUrls)
                .build();
    }
}
