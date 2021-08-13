package com.strongshop.mobile.controller;

import com.strongshop.mobile.dto.File.CompanyImageResponseDto;
import com.strongshop.mobile.dto.File.FileResponseDto;
import com.strongshop.mobile.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class FileController {

    private final FileService fileService;

    // 이미지 업로드
    // 요청에서 파일객체를 받아 DB에 저장하고 결과 반환
    @PostMapping("/api/companyImage/{companyId}")
    public List<FileResponseDto> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files, @PathVariable Long companyId) {
        List<FileResponseDto> fileDtoList = fileService.uploadFiles(files, companyId);
        return fileDtoList;
    }

}
