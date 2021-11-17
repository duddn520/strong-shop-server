package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.Image.ReviewImageUrlRepository;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.Review.ReviewRepository;
import com.strongshop.mobile.dto.Review.ReviewRequestDto;
import com.strongshop.mobile.dto.Review.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageUrlRepository reviewImageUrlRepository;
    @Transactional
    public ReviewResponseDto registerReview(Review review)
    {
        return new ReviewResponseDto(reviewRepository.save(review));
    }

    @Transactional
    public List<Review> getAllReviewsByCompanyId(Long companyId){
        List<Review> reviews = reviewRepository.findAllByCompanyIdOrderByCreatedTimeDesc(companyId)
                .orElseThrow(()-> new RuntimeException("리뷰가 존재하지 않습니다."));
        return reviews;
    }

    @Transactional
    public void updateReply(Review review, String reply){
        review.updateReply(reply);
        reviewRepository.save(review);
    }

    @Transactional
    public Review findReviewById(Long reviewId)
    {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new RuntimeException("해당 리뷰가 존재하지 않습니다."));

        return review;
    }
}
