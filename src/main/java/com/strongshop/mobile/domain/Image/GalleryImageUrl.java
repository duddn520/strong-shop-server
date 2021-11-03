package com.strongshop.mobile.domain.Image;

import com.strongshop.mobile.domain.Gallary.Gallary;
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

    private Long gallaryId;

    @Builder
    public GalleryImageUrl(Long id, String imageUrl, Long gallaryId)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.gallaryId = gallaryId;
    }

}
