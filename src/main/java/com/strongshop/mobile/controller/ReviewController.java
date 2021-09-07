package com.strongshop.mobile.controller;

import com.strongshop.mobile.dto.Gallary.GallaryRequestDto;
import com.strongshop.mobile.dto.Gallary.GallaryResponseDto;
import com.strongshop.mobile.dto.Review.ReviewRequestDto;
import com.strongshop.mobile.dto.Review.ReviewResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.FileService;
import com.strongshop.mobile.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final FileService fileService;
    private final ReviewService reviewService;

    @PostMapping("/api/{company_id}/review")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> registerReviewContent(@RequestParam("files") List<MultipartFile> files, ReviewRequestDto requestDto, @PathVariable Long company_id)
    {
        requestDto.setCompany_id(company_id);
        ReviewResponseDto responseDto = reviewService.registerReview(requestDto);
        Long review_id = responseDto.getId();

        fileService.uploadFilesToReview(files,review_id);
        responseDto = reviewService.refreshResponseDto(review_id);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);

    }
}
