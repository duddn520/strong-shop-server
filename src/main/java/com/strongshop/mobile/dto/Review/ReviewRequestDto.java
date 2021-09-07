package com.strongshop.mobile.dto.Review;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewRequestDto {

    private Long id;
    private Long company_id;
    private String content;
    private float rating;

    public Review toEntity(){
        return Review.builder()
                .content(content)
                .rating(rating)
                .build();
    }
}
