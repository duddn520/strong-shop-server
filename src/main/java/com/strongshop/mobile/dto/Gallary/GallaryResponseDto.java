package com.strongshop.mobile.dto.Gallary;

import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Gallary.GallaryImage;
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
    private List<Long> gallaryImagesId = new ArrayList<>();
    private LocalDateTime createdTime;

    public GallaryResponseDto (Gallary gallary){
        this.id = gallary.getId();
        this.company_id = gallary.getCompany().getId();
        this.content = gallary.getContent();
        for(GallaryImage gi : gallary.getGallaryImages())
            gallaryImagesId.add(gi.getId());
        this.createdTime = gallary.getCreatedTime();
    }
}
