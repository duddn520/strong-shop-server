package com.strongshop.mobile.dto.Company;

import com.strongshop.mobile.domain.Company.Company;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class CompanyRequestDto {

    private String name; // 회사명
    private String bossName; // 대표자성명
    private String address; // 주소
    private String detailAddress; // 상세주소
    private String contact; // 연락처
    private String businessNumber; // 사업자번호




    public Company toEntity() {
        return Company.builder()
                .name(name)
                .bossName(bossName)
                .address(address)
                .detailAddress(detailAddress)
                .contact(contact)
                .businessNumber(businessNumber)
                .build();

    }


}
