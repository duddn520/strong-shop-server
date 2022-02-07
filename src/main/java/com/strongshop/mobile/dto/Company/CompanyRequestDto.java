package com.strongshop.mobile.dto.Company;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.User.LoginMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CompanyRequestDto {

    private Long id;
    private String name; // 회사명
    private String email;
    private String bossName; // 대표자성명
    private String phoneNumeber;
    private String businessNumber; // 사업자번호
    private String region;
    private String fcmToken;
    private LoginMethod loginMethod;

    public Company toEntity() {
        return Company.builder()
                .id(id)
                .name(name)
                .email(email)
                .bossName(bossName)
                .phoneNumber(phoneNumeber)
                .businessNumber(businessNumber)
                .region(region)
                .fcmToken(fcmToken)
                .loginMethod(loginMethod)
                .build();

    }


}
