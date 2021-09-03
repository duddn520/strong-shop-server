package com.strongshop.mobile.dto.Gallary;

import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Gallary.GallaryImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GallaryResponseDto {
    private Long id;
    private Long company_id;
    private String content;
    private List<GallaryImage> gallaryImages;
    private LocalDateTime createdTime;

    public GallaryResponseDto (Gallary gallary){
        this.id = gallary.getId();
        this.company_id = gallary.getCompany().getId();
        this.content = gallary.getContent();
        this.gallaryImages = gallary.getGallaryImages();
        this.createdTime = gallary.getCreatedTime();
    }
}
