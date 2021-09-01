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

    private float longitude;
    private float latitude;

    private String introduction;

    @Builder
    public CompanyInfo (Company company, float longitude, float latitude, String introduction)
    {
        this.company = company;
        this.longitude = longitude;
        this.latitude = latitude;
        this.introduction = introduction;
    }

    public void updateCompany(Company company)
    {
        this.company = company;
    }

    public void updateCompanyInfo(CompanyInfo companyInfo)
    {
        this.longitude = companyInfo.getLongitude();
        this.latitude = companyInfo.getLatitude();
        this.introduction = companyInfo.getIntroduction();
    }

}
