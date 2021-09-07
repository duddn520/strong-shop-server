package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.Review.ReviewRepository;
import com.strongshop.mobile.dto.Review.ReviewRequestDto;
import com.strongshop.mobile.dto.Review.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public ReviewResponseDto registerReview(ReviewRequestDto requestDto)
    {
        Company company = companyRepository.findById(requestDto.getCompany_id())
                .orElseThrow(()->new IllegalArgumentException());

        Review review = requestDto.toEntity();
        review.updateCompany(company);

        return new ReviewResponseDto(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponseDto refreshResponseDto(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new IllegalArgumentException());

        return new ReviewResponseDto(review);
    }
}
