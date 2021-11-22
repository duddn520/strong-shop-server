package com.strongshop.mobile.domain.Gallery;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
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

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(length = 512)
    private String content;

    @OneToMany(mappedBy = "gallery",cascade = CascadeType.ALL)
    private List<GalleryImageUrl> imageUrls;

    @Builder
    public Gallery(Long id,Company company, String content, List<GalleryImageUrl> imageUrls)
    {
        this.id = id;
        this.company = company;
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
