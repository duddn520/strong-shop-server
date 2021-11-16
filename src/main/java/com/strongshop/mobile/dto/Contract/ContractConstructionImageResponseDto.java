package com.strongshop.mobile.dto.Contract;

import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import com.strongshop.mobile.domain.Image.ConstructionImageUrlRepository;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.dto.Image.ConstructionImageUrlResponseDto;
import com.strongshop.mobile.dto.Image.InspectionImageUrlResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ContractConstructionImageResponseDto {
    private Long id;
    private List<ConstructionImageUrlResponseDto> responseDtos = new ArrayList<>();

    public ContractConstructionImageResponseDto (Contract contract)
    {
        this.id = contract.getId();
        this.responseDtos = imageUrl2ResponseDto(contract);
    }

    public List<ConstructionImageUrlResponseDto> imageUrl2ResponseDto(Contract contract)
    {
        List<ConstructionImageUrl> imageUrls = contract.getConstructionImageUrls();
        List<ConstructionImageUrlResponseDto> responseDtos = new ArrayList<>();
        for(ConstructionImageUrl img : imageUrls)
        {
            ConstructionImageUrlResponseDto responseDto = new ConstructionImageUrlResponseDto(img);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }
}