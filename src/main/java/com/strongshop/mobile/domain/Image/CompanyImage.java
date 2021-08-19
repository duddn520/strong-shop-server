package com.strongshop.mobile.domain.Image;

import com.strongshop.mobile.domain.Company.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CompanyImage {

    // 파일번호 - PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사진(다) - 회사(일)
    @ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false) // 매핑할 외래키 이름지정 - company엔티티의 id필드를 외래키로 갖겠다.
    private Company company;

    // 원본파일명
    @Column(nullable = false) // null값 허용x
    private String origFilename;

    // 서버에 저장될 파일명
    @Column(nullable = false) // null값 허용x
    private String filename;

    @Column(nullable = false)
    private String filepath;


    // 전달받은 requestDto를 이용해 빌더로 객체생성하고 생성한 객체를 JPA에게 넘겨 디비에 저장
    @Builder
    public CompanyImage(Company company, String origFilename, String filename, String filepath) {
        this.company = company;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filepath = filepath;
    }
}