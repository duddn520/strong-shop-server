package com.strongshop.mobile.domain.Review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ReviewImage {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    private Review review;

    // 원본파일명
    @Column(nullable = false) // null값 허용x
    private String origFilename;

    // 서버에 저장될 파일명
    @Column(nullable = false) // null값 허용x
    private String filename;

    @Column(nullable = false)
    private String filepath;

    @Builder
    public ReviewImage(Review review, String origFilename, String filename, String filepath) {
        this.review = review;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filepath = filepath;
    }
}
