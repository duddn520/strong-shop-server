package com.strongshop.mobile.dto.Image;

import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ConstructionImageUrlResponseDto {
    private Long id;
    private String imageUrl;
    private Long contractId;

    public ConstructionImageUrlResponseDto (ConstructionImageUrl constructionImageUrl)
    {
        this.id = constructionImageUrl.getId();
        this.imageUrl = constructionImageUrl.getImageUrl();
        this.contractId = constructionImageUrl.getContract().getId();
    }

}
