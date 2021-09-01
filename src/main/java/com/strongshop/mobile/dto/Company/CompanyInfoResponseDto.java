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
    private float longitude;
    private float latitude;
    private String introduction;

    public CompanyInfoResponseDto(CompanyInfo companyInfo)
    {
        this.id = companyInfo.getId();
        this.company_id = companyInfo.getCompany().getId();
        this.longitude = companyInfo.getLongitude();
        this.latitude = companyInfo.getLatitude();
        this.introduction = companyInfo.getIntroduction();
    }
}
