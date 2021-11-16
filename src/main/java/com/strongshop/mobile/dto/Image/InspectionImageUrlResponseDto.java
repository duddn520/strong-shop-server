package com.strongshop.mobile.dto.Image;

import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InspectionImageUrlResponseDto {

    private Long id;
    private Long contractId;
    private String imageUrl;

    public InspectionImageUrlResponseDto (InspectionImageUrl inspectionImageUrl)
    {
        this.id = inspectionImageUrl.getId();
        this.contractId = inspectionImageUrl.getContract().getId();
        this.imageUrl = inspectionImageUrl.getImageUrl();
    }




}
