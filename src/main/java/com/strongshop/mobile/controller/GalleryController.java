package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.dto.Gallery.GalleryResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.FileUploadService;
import com.strongshop.mobile.service.GalleryService;
import com.strongshop.mobile.service.GalleryImageUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyService companyService;
    private final FileUploadService fileUploadService;
    private final GalleryService galleryService;



    @PostMapping("/api/gallery")
    @Transactional
    public ResponseEntity<ApiResponse<GalleryResponseDto>> registerGalleryContent(@RequestParam("files") List<MultipartFile> files, @RequestParam("content")String content, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        Gallery gallery = Gallery.builder()
                .content(content)
                .company(company)
                .build();

        List<GalleryImageUrl> imageUrls = new ArrayList<>();
        for(MultipartFile f : files){
            String filename = fileUploadService.uploadImage(f);              //이미지파일 S3에 업로드. url 반환.
            String url = fileUploadService.getFileUrl(filename);
            GalleryImageUrl imageUrl = GalleryImageUrl.builder()
                    .imageUrl(url)
                    .filename(filename)
                    .gallery(gallery)
                    .build();

            imageUrls.add(imageUrl);
        }
        //이미지 파일 url만 저장하는 DB에 저장.
        gallery.updateGalleryImageUrls(imageUrls);
        company.getGalleries().add(gallery);
        GalleryResponseDto responseDto = new GalleryResponseDto(gallery);       //DB 에 재등록.

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);

    }

    @GetMapping("/api/gallery")         //company입장에서 자신의 gallery조회
    @Transactional
    public ResponseEntity<ApiResponse<List<GalleryResponseDto>>> getAllGalleryImageUrls(HttpServletRequest request){
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        List<Gallery> galleries = company.getGalleries();
        List<GalleryResponseDto> responseDtos = new ArrayList<>();
        for(Gallery g : galleries)
        {
            GalleryResponseDto responseDto = new GalleryResponseDto(g);
            responseDtos.add(responseDto);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @GetMapping("/api/gallery/{company_id}")
    @Transactional
    public ResponseEntity<ApiResponse<List<GalleryResponseDto>>> getAllGalleryImageUrls4User(@PathVariable("company_id") Long companyId)
    {
        Company company = companyService.getCompanyById(companyId);
        List<Gallery> galleries = company.getGalleries();
        List<GalleryResponseDto> responseDtos = new ArrayList<>();
        for(Gallery g : galleries)
        {
            GalleryResponseDto responseDto = new GalleryResponseDto(g);
            responseDtos.add(responseDto);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/api/gallery/{gallery_id}")
    @Transactional
    public ResponseEntity<ApiResponse> removeGallery(@PathVariable("gallery_id") Long galleryId)
    {
        Gallery gallery = galleryService.getGalleryById(galleryId);
        List<GalleryImageUrl> urls = gallery.getImageUrls();

        for(GalleryImageUrl url : urls)
        {
            fileUploadService.removeFile(url.getFilename());
        }

        galleryService.deleteGallery(gallery);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS), HttpStatus.OK);
    }
}
