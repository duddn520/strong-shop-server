package com.strongshop.mobile.dto.Gallery;

import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.dto.Image.GalleryImageUrlResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
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
    private List<GalleryImageUrlResponseDto> imageUrls = new ArrayList<>();
    private LocalDateTime createdTime;

    public GalleryResponseDto(Gallery gallery){
        this.id = gallery.getId();
        this.company_id = gallery.getCompanyId();
        this.content = gallery.getContent();
        this.createdTime = gallery.getCreatedTime();
        this.imageUrls = makeUrlResponseDtos(gallery.getImageUrls());
    }

    public List<GalleryImageUrlResponseDto> makeUrlResponseDtos(List<GalleryImageUrl> imageUrls)
    {
        List<GalleryImageUrlResponseDto> responseDtos = new ArrayList<>();
        for(GalleryImageUrl img : imageUrls)
        {
            GalleryImageUrlResponseDto responseDto = new GalleryImageUrlResponseDto(img);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
}
