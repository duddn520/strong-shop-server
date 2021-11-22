package com.strongshop.mobile.domain.Image;

import com.strongshop.mobile.domain.Review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ReviewImageUrl {

    @Id
    @GeneratedValue
    private Long id;
    private String imageUrl;
    private String filename;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;


    @Builder
    public ReviewImageUrl(Long id, String imageUrl,String filename, Review review) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.filename = filename;
        this.review = review;

    }

    public void updateReviewId(Review review){
        this.review = review;
    }
}
