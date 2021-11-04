package com.strongshop.mobile.dto.Gallery;

import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GalleryResponseDto {
    private Long id;
    private Long company_id;
    private String content;
    private List<GalleryImageUrl> imageUrls = new ArrayList<>();
    private LocalDateTime createdTime;

    public GalleryResponseDto(Gallery gallery){
        this.id = gallery.getId();
        this.company_id = gallery.getCompanyId();
        this.content = gallery.getContent();
        this.createdTime = gallery.getCreatedTime();
        this.imageUrls = gallery.getImageUrls();
    }
}
