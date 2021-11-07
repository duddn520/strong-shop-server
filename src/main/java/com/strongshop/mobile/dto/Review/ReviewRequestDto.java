package com.strongshop.mobile.dto.Review;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.Review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ReviewRequestDto {

    private Long id;
    private Long company_id;
    private String content;
    private float rating;
    private List<ReviewImageUrl> reviewImageUrls = new ArrayList<>();
    private String reply;

    public Review toEntity(){
        return Review.builder()
                .companyId(company_id)
                .content(content)
                .rating(rating)
                .reviewImageUrls(reviewImageUrls)
                .reply(reply)
                .build();
    }
}
