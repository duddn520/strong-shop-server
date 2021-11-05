package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.Image.ReviewImageUrlRepository;
import com.strongshop.mobile.dto.Image.ReviewImageUrlRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageUrlService {

    private final ReviewImageUrlRepository reviewImageUrlRepository;

    @Transactional
    public List<ReviewImageUrl> registerReviewImageUrl(List<String> imageUrls, Long reviewId)
    {
        ReviewImageUrlRequestDto requestDto = new ReviewImageUrlRequestDto();
        List<ReviewImageUrl> reviewImageUrls = new ArrayList<>();
        requestDto.setReviewId(reviewId);
        for (String i : imageUrls)
        {
            requestDto.setImageUrl(i);
            ReviewImageUrl imageUrl = requestDto.toEntity();
            reviewImageUrls.add(imageUrl);
        }
        return reviewImageUrls;
    }

}
