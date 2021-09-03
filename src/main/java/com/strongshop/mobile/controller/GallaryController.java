package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Gallary.Gallary;
import com.strongshop.mobile.domain.Gallary.GallaryImage;
import com.strongshop.mobile.dto.File.FileResponseDto;
import com.strongshop.mobile.dto.Gallary.GallaryRequestDto;
import com.strongshop.mobile.dto.Gallary.GallaryResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.FileService;
import com.strongshop.mobile.service.GallaryService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.pool.TypePool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GallaryController {

    private final FileService fileService;
    private final GallaryService gallaryService;
    private final CompanyRepository companyRepository;

    @PostMapping("/api/{company_id}/gallary")
    public ResponseEntity<ApiResponse<GallaryResponseDto>> registerGallaryContent(@RequestParam("files") List<MultipartFile> files, @RequestBody GallaryRequestDto requestDto,@PathVariable Long company_id)
    {
        requestDto.setCompany_id(company_id);
        GallaryResponseDto responseDto = gallaryService.registerGallary(requestDto);
        Long gallary_id = responseDto.getId();

        fileService.uploadFilesToGallary(files,gallary_id);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);

    }

    @GetMapping("/api/{company_id}/gallary")
    public ResponseEntity<ApiResponse<List<FileResponseDto>>> getGallaryThumbnailImages(@PathVariable Long company_id)
    {
        Company company = companyRepository.findById(company_id)
                .orElseThrow(()->new IllegalArgumentException());

        List<Gallary> gallaries = gallaryService.findGallariesByCompany(company);

        List<FileResponseDto> fileResponseDtos = new ArrayList<>();

        for(Gallary g : gallaries)
        {
            GallaryImage gi = g.getGallaryImages().get(0);
            fileResponseDtos.add(FileResponseDto.builder()
                            .id(gi.getId())
                    .realationId(g.getId())
                    .origFilename(gi.getOrigFilename())
                    .filename(gi.getFilename())
                    .filepath(gi.getFilepath())
                    .build()
            );

        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                fileResponseDtos),HttpStatus.CREATED);
    }

}
