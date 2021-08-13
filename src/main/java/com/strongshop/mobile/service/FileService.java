package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Image.CompanyImage;
import com.strongshop.mobile.domain.Image.CompanyImageRepository;
import com.strongshop.mobile.dto.File.CompanyImageRequestDto;
import com.strongshop.mobile.dto.File.CompanyImageResponseDto;
import com.strongshop.mobile.dto.File.FileRequestDto;
import com.strongshop.mobile.dto.File.FileResponseDto;
import com.strongshop.mobile.exception.AttachFileException;
import com.strongshop.mobile.exception.CompanyNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final CompanyImageRepository companyImageRepository;
    private final CompanyRepository companyRepository;

    private String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MultipartFile[]타입의 files에는 업로드할 파일의 정보가 담겨있음
    public List<FileResponseDto> uploadFiles(List<MultipartFile> files, Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));

        //업로드 파일 정보를 담을 비어있는 리스트 수정수정 랼랄라
        List<FileResponseDto> attachList = new ArrayList<>();

        // 파일이 비어있으면 비어있는 리스트 반환
        if (files.isEmpty()){
            return attachList;
        }

        // 여기다가 저장할것임!!!
        // local_path
        String path = "/usr/local/etc/nginx/images";
        // ec2_path
        //String path = "/etc/nginx/images";

        // 파일 개수만큼 forEach실행
        for (MultipartFile file : files) {
            try {
                final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                final String saveName = getRandomString() + "." + extension;

                java.io.File target = new java.io.File(path, saveName);

                // 물리적으로 파일을 생성
                file.transferTo(target);

                // 제네릭으로 런타임에 타입지정
                FileRequestDto<Company> fileRequestDto = FileRequestDto.<Company>builder()
                        .t(company)
                        .origFilename(file.getOriginalFilename())
                        .filename(saveName)
                        .filepath(path)
                        .build();


                CompanyImage entity = companyImageRepository.save(fileRequestDto.toCompanyImage());

                log.info("File save complete...");
                log.info(entity.getFilepath());
                log.info(entity.getFilename());
                // 파일 정보 추가
                attachList.add(FileResponseDto.builder()
                                    .id(entity.getId())
                                    .realationId(entity.getCompany().getId())
                                    .filename(entity.getFilename())
                                    .origFilename(entity.getOrigFilename())
                                    .filepath(entity.getFilepath())
                                    .build());

            } catch (Exception e) {
                throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
            }
        }
        // 파일정보를 담은 리스트 반환
        return attachList;
    }

    @Transactional
    public List<FileResponseDto> getFile(Long companyId) {
        List<CompanyImage> imageList = companyImageRepository.findAllByCompanyId(companyId);
        List<FileResponseDto> fileDtoList = new ArrayList<>();

        for (CompanyImage image : imageList) {
            FileResponseDto responseDto = FileResponseDto.builder()
                    .id(image.getId())
                    .realationId(image.getCompany().getId())
                    .filename(image.getFilename())
                    .filepath(image.getFilepath())
                    .origFilename(image.getOrigFilename())
                    .build();

            fileDtoList.add(responseDto);
        }
        return fileDtoList;
    }

    @Transactional
    public void deleteFile(Long fileId){
        companyImageRepository.deleteById(fileId);
    }
}
