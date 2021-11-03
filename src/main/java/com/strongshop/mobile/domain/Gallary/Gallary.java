package com.strongshop.mobile.domain.Gallary;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Gallary extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long companyId;
    private String content;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GalleryImageUrl> imageUrls;

    @Builder
    public Gallary(Long id, Long companyId, String content, List<GalleryImageUrl> imageUrls)
    {
        this.id = id;
        this.companyId = companyId;
        this.content = content;
        this.imageUrls = imageUrls;
    }

    public void updateImageUrls(List<GalleryImageUrl> imageUrls)
    {
        this.imageUrls = imageUrls;
    }

}
