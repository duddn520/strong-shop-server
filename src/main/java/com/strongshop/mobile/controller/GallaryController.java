package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.dto.Gallary.GallaryRequestDto;
import com.strongshop.mobile.dto.Gallary.GallaryResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.FileUploadService;
import com.strongshop.mobile.service.GallaryService;
import com.strongshop.mobile.service.GalleryImageUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GallaryController {

    private final JwtTokenProvider jwtTokenProvider;
    private final GalleryImageUrlService galleryImageUrlService;
    private final CompanyService companyService;
    private final FileUploadService fileUploadService;
    private final GallaryService gallaryService;



    @PostMapping("/api/gallary")
    public ResponseEntity<ApiResponse<GallaryResponseDto>> registerGallaryContent(@RequestParam("files") List<MultipartFile> files,@RequestParam("content")String content, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        GallaryRequestDto requestDto = new GallaryRequestDto();

        requestDto.setCompany_id(company.getId());
        requestDto.setContent(content);
        List<GalleryImageUrl> imageUrls = new ArrayList<>();
        List<String> urllist = new ArrayList<>();
        for(MultipartFile f : files){
            urllist.add(fileUploadService.uploadImage(f));              //이미지파일 S3에 업로드. url 반환.
        }
        GallaryResponseDto responseDto = new GallaryResponseDto(gallaryService.registerGallary(requestDto));        //갤러리 id를 얻기위해 일단 등록.
        Long gallaryId = responseDto.getId();
        imageUrls = galleryImageUrlService.registerGalleryImageUrl(urllist,gallaryId);         //이미지 파일 url만 저장하는 DB에 저장.
        requestDto.setGalleryImageUrls(imageUrls);              //urllist추가된 requestdto
        responseDto = new GallaryResponseDto(gallaryService.updateGallary(requestDto,responseDto.getId()));       //DB 에 재등록.

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);

    }
//
//    @GetMapping("/api/{company_id}/gallary")
//    public ResponseEntity<ApiResponse<>> getGallaryThumbnailImages(@PathVariable Long company_id)
//    {
//        Company company = companyRepository.findById(company_id)
//                .orElseThrow(()->new IllegalArgumentException());
//
//        List<Gallary> gallaries = gallaryService.findGallariesByCompany(company);
//
//        List<FileResponseDto> fileResponseDtos = new ArrayList<>();
//
//        for(Gallary g : gallaries)
//        {
//            GallaryImage gi = g.getGallaryImages().get(0);
//            fileResponseDtos.add(FileResponseDto.builder()
//                            .id(gi.getId())
//                    .realationId(g.getId())
//                    .origFilename(gi.getOrigFilename())
//                    .filename(gi.getFilename())
//                    .filepath(gi.getFilepath())
//                    .build()
//            );
//
//        }
//
//        return new ResponseEntity<>(ApiResponse.response(
//                HttpStatusCode.OK,
//                HttpResponseMsg.GET_SUCCESS,
//                fileResponseDtos),HttpStatus.CREATED);
//    }

}
