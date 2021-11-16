package com.strongshop.mobile.dto.Contract;

import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.dto.Image.InspectionImageUrlResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ContractInspectionImageResponseDto {
    private Long id;
    private List<InspectionImageUrlResponseDto> imageUrlResponseDtos = new ArrayList<>();

    public ContractInspectionImageResponseDto (Contract contract)
    {
        this.id = contract.getId();
        this.imageUrlResponseDtos = imageUrl2ResponseDto(contract);
    }

    public List<InspectionImageUrlResponseDto> imageUrl2ResponseDto(Contract contract)
    {
        List<InspectionImageUrl> imageUrls = contract.getInspectionImageUrls();
        List<InspectionImageUrlResponseDto> responseDtos = new ArrayList<>();
        for(InspectionImageUrl img : imageUrls)
        {
            InspectionImageUrlResponseDto responseDto = new InspectionImageUrlResponseDto(img);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
}
