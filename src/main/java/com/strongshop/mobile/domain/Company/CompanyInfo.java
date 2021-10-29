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
    private String introduction;
    private String blogUrl;
    private String siteUrl;
    private String snsUrl;
    private String contact;
    private String address;
    private String detailAddress;


    @Builder
    public CompanyInfo (Long id, Company company, String introduction, String blogUrl, String siteUrl, String snsUrl, String contact, String address, String detailAddress)
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
    }

    public void updateCompany(Company company)
    {
        this.company = company;
    }

    public void updateCompanyInfo(CompanyInfo companyInfo)
    {
        this.introduction = companyInfo.getIntroduction();
    }

}
