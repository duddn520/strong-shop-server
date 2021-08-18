package com.strongshop.mobile.dto.File;

import com.strongshop.mobile.domain.Company.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyImageRequestDto {

    private Company company;
    private String origFilename;
    private String filename;
    private String filepath;

    @Builder
    public CompanyImageRequestDto(Company company, String origFilename, String filename, String filepath) {
        this.company = company;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filepath = filepath;
    }
}
