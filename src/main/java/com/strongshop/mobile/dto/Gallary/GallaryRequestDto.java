package com.strongshop.mobile.dto.Gallary;

import com.strongshop.mobile.domain.Gallary.Gallary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class GallaryRequestDto {

    private Long id;
    private Long company_id;
    private String content;


    public Gallary toEntity(){
        return Gallary.builder()
                .content(content)
                .build();
    }
}
