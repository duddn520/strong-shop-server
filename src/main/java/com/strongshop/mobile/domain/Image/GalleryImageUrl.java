package com.strongshop.mobile.domain.Image;

import com.strongshop.mobile.domain.Gallery.Gallery;
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
    private String filename;

    @ManyToOne
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @Builder
    public GalleryImageUrl(Long id, String imageUrl,String filename, Gallery gallery)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.filename = filename;
        this.gallery = gallery;
    }

    public void updateGalleryId(Gallery gallery)
    {
        this.gallery = gallery;
    }

}
