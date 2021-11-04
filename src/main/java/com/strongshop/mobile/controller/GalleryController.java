package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.dto.Gallery.GalleryRequestDto;
import com.strongshop.mobile.dto.Gallery.GalleryResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.AWSS3DownloadService;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.FileUploadService;
import com.strongshop.mobile.service.GalleryService;
import com.strongshop.mobile.service.GalleryImageUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final JwtTokenProvider jwtTokenProvider;
    private final GalleryImageUrlService galleryImageUrlService;
    private final CompanyService companyService;
    private final FileUploadService fileUploadService;
    private final GalleryService galleryService;
    private final AWSS3DownloadService awss3DownloadService;



    @PostMapping("/api/gallery")
    public ResponseEntity<ApiResponse<GalleryResponseDto>> registerGalleryContent(@RequestParam("files") List<MultipartFile> files, @RequestParam("content")String content, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        GalleryRequestDto requestDto = new GalleryRequestDto();

        requestDto.setCompany_id(company.getId());
        requestDto.setContent(content);
        List<GalleryImageUrl> imageUrls = new ArrayList<>();
        List<String> urllist = new ArrayList<>();
        for(MultipartFile f : files){
            urllist.add(fileUploadService.uploadImage(f));              //이미지파일 S3에 업로드. url 반환.
        }
        Gallery gallery = new Gallery();
        galleryService.registerGallery(gallery);
        System.out.println("gallery.getId() = " + gallery.getId());
        imageUrls = galleryImageUrlService.registerGalleryImageUrl(urllist,gallery.getId());         //이미지 파일 url만 저장하는 DB에 저장.
        requestDto.setGalleryImageUrls(imageUrls);
        galleryService.updateGalleryEntity(gallery,requestDto);
        GalleryResponseDto responseDto = new GalleryResponseDto(gallery);       //DB 에 재등록.

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);

    }

    @GetMapping("/api/gallery")         //자신 회사의 전체 갤러리 리스트 조회
    public ResponseEntity<ApiResponse<List<GalleryResponseDto>>> downloadAllGalleries(HttpServletRequest request) throws IOException{
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        Long companyId = company.getId();

        List<Gallery> galleries = galleryService.getAllGalleriesByCompanyId(companyId);
        List<GalleryResponseDto> responseDtos = new ArrayList<>();                                                  //GalleryResponseDto에 List<responseEntity<byte[]>> 생성되어 있음.
        for(Gallery g :galleries)
        {
            GalleryResponseDto responseDto = new GalleryResponseDto(g);
            List<ResponseEntity<byte[]>> responseEntities = new ArrayList<>();
            List<GalleryImageUrl> galleryImageUrls = g.getImageUrls();
            for(GalleryImageUrl img : galleryImageUrls)                                             //각 갤러리마다 모든 사진 다운로드 후 responseDto에 저장.
            {
                ResponseEntity<byte[]> imgResponseEntity = awss3DownloadService.objectToFile(awss3DownloadService.downloadFile(img.getImageUrl()), img.getImageUrl());
                responseEntities.add(imgResponseEntity);
            }
            responseDto.setResponsepictures(responseEntities);
            responseDtos.add(responseDto);                                          //responseDto들의 뭉치 저장.
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);

    }

}
