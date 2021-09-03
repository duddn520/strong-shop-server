package com.strongshop.mobile.domain.Gallary;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class GallaryImage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Gallary gallary;

    // 원본파일명
    @Column(nullable = false) // null값 허용x
    private String origFilename;

    // 서버에 저장될 파일명
    @Column(nullable = false) // null값 허용x
    private String filename;

    @Column(nullable = false)
    private String filepath;

    @Builder
    public GallaryImage(Gallary gallary,String origFilename, String filename, String filepath) {
        this.gallary = gallary;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filepath = filepath;
    }
}
