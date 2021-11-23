package com.strongshop.mobile.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.strongshop.mobile.S3.S3Component;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
@Service
public class AWSS3DownloadService implements DownloadService{

    private final AmazonS3 amazonS3;
    private final S3Component component;

    @Override
    public S3Object downloadFile(String filename) {
        return amazonS3.getObject(component.getBucket(),filename);
    }

    public ResponseEntity<byte[]> objectToFile(S3Object object, String storedFilename) throws IOException{
        S3ObjectInputStream inputStream = object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(inputStream);

        String fileName = URLEncoder.encode(storedFilename,"UTF-8");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment",fileName);

        return new ResponseEntity<>(bytes, httpHeaders,HttpStatus.OK);
    }


}
