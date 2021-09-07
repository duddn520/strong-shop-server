package com.strongshop.mobile.dto.File;

import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Gallary.GallaryImage;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.Review.ReviewImage;
import lombok.Builder;

public class FileRequestDto<T> {

    private T relationEntity;
    private String origFilename;
    private String filename;
    private String filepath;

    @Builder
    public FileRequestDto(T t, String origFilename, String filename, String filepath) {
        this.relationEntity = t;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filepath = filepath;
    }

    public GallaryImage toGallaryImage() {
        return GallaryImage.builder()
                .gallary((Gallary) relationEntity)
                .origFilename(origFilename)
                .filename(filename)
                .filepath(filepath)
                .build();
    }

    public ReviewImage toReviewImage(){
        return ReviewImage.builder()
                .review((Review) relationEntity)
                .origFilename(origFilename)
                .filename(filename)
                .filepath(filepath)
                .build();
    }
}
