package com.strongshop.mobile.domain.Image;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class GalleryImageUrl {

    @Id @GeneratedValue
    private Long id;
    private String imageUrl;

    private Long galleryId;

    @Builder
    public GalleryImageUrl(Long id, String imageUrl, Long galleryId)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.galleryId = galleryId;
    }

}
