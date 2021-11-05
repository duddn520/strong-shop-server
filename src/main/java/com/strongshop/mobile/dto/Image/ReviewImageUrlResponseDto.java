package com.strongshop.mobile.dto.Image;

import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewImageUrlResponseDto {

    private Long id;
    private Long reviewId;
    private String imageUrl;

    public ReviewImageUrlResponseDto(ReviewImageUrl imageUrl)
    {
        this.id = imageUrl.getId();
        this.reviewId = imageUrl.getReview().getId();
        this.imageUrl = imageUrl.getImageUrl();
    }
}
