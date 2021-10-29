package com.strongshop.mobile.dto.Company;

import com.strongshop.mobile.domain.Company.Company;
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
    private String address; // 주소
    private String detailAddress; // 상세주소
    private String phoneNumeber;
    private String contact; // 연락처
    private String businessNumber; // 사업자번호




    public Company toEntity() {
        return Company.builder()
                .id(id)
                .name(name)
                .email(email)
                .bossName(bossName)
                .address(address)
                .detailAddress(detailAddress)
                .phoneNumber(phoneNumeber)
                .businessNumber(businessNumber)
                .build();

    }


}
