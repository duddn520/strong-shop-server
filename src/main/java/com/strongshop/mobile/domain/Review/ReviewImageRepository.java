package com.strongshop.mobile.domain.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
    List<ReviewImage> findAllByReviewId(Long Id);
}
