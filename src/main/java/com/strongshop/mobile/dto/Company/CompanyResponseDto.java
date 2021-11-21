package com.strongshop.mobile.dto.Company;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.User.LoginMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyResponseDto {


    private Long id;
    private String name; // 회사명
    private String email;
    private String bossName; // 대표자성명
    private String phoneNumber;
    private String businessNumber; // 사업자번호
    private LoginMethod loginMethod;



    public CompanyResponseDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.email = company.getEmail();
        this.bossName = company.getBossName();
        this.phoneNumber = company.getPhoneNumber();
        this.businessNumber = company.getBusinessNumber();
        this.loginMethod = company.getLoginMethod();
    }
}
