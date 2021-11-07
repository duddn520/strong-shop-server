package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.Image.ReviewImageUrlRepository;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.dto.Review.ReviewRequestDto;
import com.strongshop.mobile.dto.Review.ReviewResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.FileUploadService;
import com.strongshop.mobile.service.ReviewImageUrlService;
import com.strongshop.mobile.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReviewImageUrlService reviewImageUrlService;
    private final CompanyService companyService;
    private final FileUploadService fileUploadService;
    private final ReviewService reviewService;

    @PostMapping("/api/review")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> registerReviewContent(@RequestParam("files") List<MultipartFile> files, @RequestParam("content")String content, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        ReviewRequestDto requestDto = new ReviewRequestDto();

        requestDto.setCompany_id(company.getId());
        requestDto.setContent(content);
        List<ReviewImageUrl> imageUrls = new ArrayList<>();
        List<String> urllist = new ArrayList<>();
        for(MultipartFile f : files)
        {
            urllist.add(fileUploadService.uploadImage(f));
        }
        Review review = new Review();
        reviewService.registerReview(review);
        imageUrls = reviewImageUrlService.registerReviewImageUrl(urllist,review.getId());
        requestDto.setReviewImageUrls(imageUrls);
        reviewService.updateReviewEntity(review,requestDto);
        ReviewResponseDto responseDto = new ReviewResponseDto(review);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/review")
    @Transactional
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getAllReviewImageUrls(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        List<Review> reviews = reviewService.getAllReviewsByCompanyId(company.getId());
        List<ReviewResponseDto> responseDtos = new ArrayList<>();
        for(Review r : reviews)
        {
            ReviewResponseDto responseDto = new ReviewResponseDto(r);
            responseDtos.add(responseDto);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @PutMapping("/api/review/reply")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> makeReply(@RequestBody ReviewRequestDto requestDto)
    {
        Long reviewId = requestDto.getId();
        Review review = reviewService.findReviewById(reviewId);
        reviewService.updateReply(review,requestDto.getReply());
        ReviewResponseDto responseDto = new ReviewResponseDto(review);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto), HttpStatus.OK);

    }
}
