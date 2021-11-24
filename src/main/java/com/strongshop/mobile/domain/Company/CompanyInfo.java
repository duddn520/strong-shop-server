package com.strongshop.mobile.domain.Company;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CompanyInfo {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(length = 2500)
    private String introduction;

    private String blogUrl;
    private String siteUrl;
    private String snsUrl;
    private String contact;
    private String address;
    private String detailAddress;
    private String backgroundImageUrl;
    private String backgroundFilename;
    private String latitude;
    private String longitude;



    @Builder
    public CompanyInfo (Long id, Company company, String introduction, String blogUrl, String siteUrl, String snsUrl, String contact, String address, String detailAddress, String backgroundImageUrl,String backgroundFilename, String latitude, String longitude)
    {
        this.id = id;
        this.company = company;
        this.introduction = introduction;
        this.blogUrl = blogUrl;
        this.snsUrl = snsUrl;
        this.siteUrl = siteUrl;
        this.contact = contact;
        this.address = address;
        this.detailAddress = detailAddress;
        this.backgroundImageUrl = backgroundImageUrl;
        this.backgroundFilename = backgroundFilename;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CompanyInfo updateBackgroundImageUrl(String url,String filename)
    {
        this.backgroundImageUrl = url;
        this.backgroundFilename = filename;
        return this;
    }

}
