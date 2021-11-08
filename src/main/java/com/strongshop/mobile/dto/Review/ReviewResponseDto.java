package com.strongshop.mobile.dto.Review;

import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.Image.GalleryImageUrlResponseDto;
import com.strongshop.mobile.dto.Image.ReviewImageUrlRequestDto;
import com.strongshop.mobile.dto.Image.ReviewImageUrlResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private LocalDateTime createdTime;
    private List<ReviewImageUrlResponseDto> imageUrls = new ArrayList<>();
    private String reply;
    private String userThumbnailImage;
    private String userNickName;

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.companyId = review.getCompanyId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createdTime = review.getCreatedTime();
        this.imageUrls = makeUrlResponseDtos(review.getReviewImageUrls());
        this.reply= review.getReply();
        this.userThumbnailImage = review.getUser().getThumbnailImage();
        this.userNickName = review.getUser().getNickname();

    }

    public List<ReviewImageUrlResponseDto> makeUrlResponseDtos(List<ReviewImageUrl> imageUrls)
    {
        List<ReviewImageUrlResponseDto> responseDtos = new ArrayList<>();
        for (ReviewImageUrl img : imageUrls)
        {
            ReviewImageUrlResponseDto responseDto = new ReviewImageUrlResponseDto(img);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
}
