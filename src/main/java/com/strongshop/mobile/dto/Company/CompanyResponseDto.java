package com.strongshop.mobile.dto.Company;

import com.strongshop.mobile.domain.Company.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyResponseDto {


    private Long id;
    private String name; // 회사명
    private String bossName; // 대표자성명
    private String address; // 주소
    private String detailAddress; // 상세주소
    private String contact; // 연락처
    private String businessNumber; // 사업자번호


    public CompanyResponseDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.bossName = company.getBossName();
        this.address = company.getAddress();
        this.detailAddress = company.getDetailAddress();
        this.contact = company.getContact();
        this.businessNumber = company.getBusinessNumber();
    }
}
