package com.strongshop.mobile.dto.File;


import lombok.Builder;

public class FileResponseDto {

    private Long id;
    private Long realationId;
    private String origFilename;
    private String filename;
    private String filepath;

    @Builder
    public FileResponseDto(Long id, Long realationId, String origFilename, String filename, String filepath) {
        this.id = id;
        this.realationId = realationId;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filepath = filepath;
    }
}
