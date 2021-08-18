package com.strongshop.mobile.dto.File;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Image.CompanyImage;
import lombok.Builder;

public class FileRequestDto<T> {

    private T relationEntity;
    private String origFilename;
    private String filename;
    private String filepath;

    @Builder
    public FileRequestDto(T t, String origFilename, String filename, String filepath) {
        this.relationEntity = t;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filepath = filepath;
    }

    public CompanyImage toCompanyImage() {
        return CompanyImage.builder()
                .company((Company) relationEntity)
                .filename(filename)
                .origFilename(origFilename)
                .filename(filename)
                .filepath(filepath)
                .build();
    }
}
