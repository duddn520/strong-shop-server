package com.strongshop.mobile.dto.Image;

import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.Review.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewImageUrlRequestDto {

    private Long id;
    private Long reviewId;
    private String imageUrl;

    public ReviewImageUrl toEntity(){
        return ReviewImageUrl.builder()
                .id(id)
                .imageUrl(imageUrl)
                .build();
    }
}
