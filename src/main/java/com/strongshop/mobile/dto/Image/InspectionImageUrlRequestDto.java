package com.strongshop.mobile.dto.Image;

import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InspectionImageUrlRequestDto {
    private Long id;
    private Long contractId;
    private String imageUrl;

    public InspectionImageUrl toEntity()
    {
        return InspectionImageUrl.builder()
                .id(id)
                .imageUrl(imageUrl)
                .build();
    }
}
