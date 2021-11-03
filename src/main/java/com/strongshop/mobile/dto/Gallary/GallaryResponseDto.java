package com.strongshop.mobile.dto.Gallary;

import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.dto.GalleryImageUrl.GalleryImageUrlResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GallaryResponseDto {
    private Long id;
    private Long company_id;
    private String content;
    private List<GalleryImageUrl> imageUrls = new ArrayList<>();
    private LocalDateTime createdTime;

    public GallaryResponseDto (Gallary gallary){
        this.id = gallary.getId();
        this.company_id = gallary.getCompanyId();
        this.content = gallary.getContent();
        this.createdTime = gallary.getCreatedTime();
        this.imageUrls = gallary.getImageUrls();
    }
}
