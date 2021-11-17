package com.strongshop.mobile.dto.Company;

import com.strongshop.mobile.domain.Company.CompanyInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CompanyInfoResponseDto {
    private Long id;
    private Long company_id;
    private String company_name;
    private String introduction;
    private String blogUrl;
    private String siteUrl;
    private String snsUrl;
    private String contact;
    private String address;
    private String detailAddress;
    private String backgroundImageUrl;
    private String longitude;
    private String latitude;


    public CompanyInfoResponseDto(CompanyInfo companyInfo)
    {
        this.id = companyInfo.getId();
        this.company_id = companyInfo.getCompany().getId();
        this.company_name = companyInfo.getCompany().getName();
        this.introduction = companyInfo.getIntroduction();
        this.blogUrl = companyInfo.getBlogUrl();
        this.siteUrl = companyInfo.getSiteUrl();
        this.snsUrl = companyInfo.getSnsUrl();
        this.contact = companyInfo.getContact();
        this.address = companyInfo.getAddress();
        this.detailAddress = companyInfo.getDetailAddress();
        this.backgroundImageUrl = companyInfo.getBackgroundImageUrl();
        this.longitude = companyInfo.getLongitude();
        this.latitude = companyInfo.getLatitude();
    }
}
