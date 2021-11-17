package com.strongshop.mobile.domain.Gallery;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.dto.Gallery.GalleryRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Gallery extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long companyId;
    private String content;

    @OneToMany(mappedBy = "gallery",cascade = CascadeType.ALL)
    private List<GalleryImageUrl> imageUrls;

    @Builder
    public Gallery(Long id, Long companyId, String content, List<GalleryImageUrl> imageUrls)
    {
        this.id = id;
        this.companyId = companyId;
        this.content = content;
        this.imageUrls = imageUrls;
    }

    public void updateGalleryImageUrls(List<GalleryImageUrl> imageUrls)
    {
        this.imageUrls = imageUrls;
    }

    public void updateGalleryIdToUrls(List<GalleryImageUrl> imageUrls)
    {
        for(GalleryImageUrl img : imageUrls)
        {
            img.updateGalleryId(this);
        }
    }

}
