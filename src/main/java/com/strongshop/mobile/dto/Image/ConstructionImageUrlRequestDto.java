package com.strongshop.mobile.dto.Image;

import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConstructionImageUrlRequestDto {

    private Long id;
    private Long contractId;
    private String imageUrl;

    public ConstructionImageUrl toEntity()
    {
        return ConstructionImageUrl.builder()
                .id(id)
                .imageUrl(imageUrl)
                .build();
    }
}
