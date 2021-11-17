package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.Image.ReviewImageUrlRepository;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.User.User;
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
import com.strongshop.mobile.service.UserService;
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
    private final UserService userService;

    @PostMapping("/api/review/{company_id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> registerReviewContent(@RequestParam("files") List<MultipartFile> files, @RequestParam("content")String content,@RequestParam("rating") float rating,@PathVariable("company_id") Long companyId ,HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);
        Review review = Review.builder()
                .user(user)
                .rating(rating)
                .companyId(companyId)
                .content(content)
                .build();

        List<ReviewImageUrl> imageUrls = new ArrayList<>();
        for(MultipartFile f : files)
        {
            String url = fileUploadService.uploadImage(f);
            ReviewImageUrl imageUrl = ReviewImageUrl.builder()
                    .imageUrl(url)
                    .review(review)
                    .build();

            imageUrls.add(imageUrl);
        }
        review.updateReviewImageUrls(imageUrls);
        reviewService.registerReview(review);
        ReviewResponseDto responseDto = new ReviewResponseDto(review);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/review")          //회사가 조회하는 경우.
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
    public ResponseEntity<ApiResponse> makeReply(@RequestBody ReviewRequestDto requestDto)
    {
        Long reviewId = requestDto.getId();
        Review review = reviewService.findReviewById(reviewId);
        reviewService.updateReply(review,requestDto.getReply());

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS), HttpStatus.OK);

    }

    @GetMapping("/api/review/{company_id}")         //유저가 조회하는 경우, companyid 수동으로 공급.
    @Transactional
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getAllReviewImageUrls4User(@PathVariable("company_id") Long companyId, HttpServletRequest request)
    {
        List<Review> reviews = reviewService.getAllReviewsByCompanyId(companyId);
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
}
