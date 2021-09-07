package com.strongshop.mobile.dto.Review;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.Review.ReviewImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private Long companyId;
    private String content;
    private float rating;
    private List<Long> reviewImagesId = new ArrayList<>();

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.companyId = review.getCompany().getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        for(ReviewImage ri : review.getReviewImages())
            this.reviewImagesId.add(ri.getId());
    }
}
