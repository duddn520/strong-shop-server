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

    private float longitude;
    private float latitude;
    private String introduction;

    public CompanyInfo toEntity(){
        return CompanyInfo.builder()
                .longitude(longitude)
                .latitude(latitude)
                .introduction(introduction)
                .build();
    }
}
