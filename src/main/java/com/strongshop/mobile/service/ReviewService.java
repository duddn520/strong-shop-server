package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.Review.ReviewRepository;
import com.strongshop.mobile.domain.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

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

    @Transactional
    public List<Review> findAllReviewsByUser(User user)
    {
        List<Review> reviews = reviewRepository.findAllByUser(user)
                .orElseGet(()->new ArrayList<>());
        return reviews;
    }
}
