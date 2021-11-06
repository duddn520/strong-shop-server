package com.strongshop.mobile.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileUploadService {

    private final UploadService uploadService;

    public String uploadImage(MultipartFile file){
        String filename = createFileName(file.getOriginalFilename());
        log.info(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try(InputStream inputStream = file.getInputStream()){
            uploadService.uploadFile(inputStream,objectMetadata,filename);
        }
        catch (IOException e) {
            throw new IllegalArgumentException(String.format("file 변환 에러. (%s)", file.getOriginalFilename()));
        }
        return uploadService.getFileUrl(filename);
    }

    private String createFileName(String originalFileName)
    {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String filename)
    {
        try{
            return filename.substring(filename.lastIndexOf("."));
        }catch (StringIndexOutOfBoundsException e)
        {
            throw new IllegalArgumentException(String.format("잘못된 형식의 file (%s)",filename));
        }
    }
}