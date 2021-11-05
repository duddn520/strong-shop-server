package com.strongshop.mobile.domain.Review;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.dto.Review.ReviewRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Review extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private Long companyId;

    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ReviewImageUrl> reviewImageUrls = new ArrayList<>();

    private String content;
    private float rating;

    @Builder
    public Review(Long id,Long companyId, String content, float rating, List<ReviewImageUrl> reviewImageUrls)
    {
        this.id = id;
        this.companyId= companyId;
        this.content = content;
        this.rating = rating;
        this.reviewImageUrls = reviewImageUrls;
    }

    public void updateReview(ReviewRequestDto requestDto)
    {
        this.companyId = requestDto.getCompany_id();
        this.content = requestDto.getContent();
        this.reviewImageUrls = requestDto.getReviewImageUrls();
    }

    public void updateReviewIdToUrls(List<ReviewImageUrl> imageUrls)
    {
        for(ReviewImageUrl img : imageUrls){
            img.updateReviewId(this);
        }
    }

}
