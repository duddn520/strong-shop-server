package com.strongshop.mobile.dto.Company;

import com.strongshop.mobile.domain.Company.CompanyInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CompanyInfoRequestDto {

    private Long id;
    private Long company_id;
    private String introduction;
    private String blogUrl;
    private String siteUrl;
    private String snsUrl;
    private String contact;
    private String address;
    private String detailAddress;
    private String backgroundImageUrl;
    private String latitude;
    private String longitude;

    public CompanyInfo toEntity(){
        return CompanyInfo.builder()
                .introduction(introduction)
                .blogUrl(blogUrl)
                .siteUrl(siteUrl)
                .snsUrl(snsUrl)
                .contact(contact)
                .address(address)
                .detailAddress(detailAddress)
                .backgroundImageUrl(backgroundImageUrl)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
