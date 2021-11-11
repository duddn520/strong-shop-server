package com.strongshop.mobile.dto.Contract;

import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.dto.Image.ConstructionImageUrlResponseDto;
import com.strongshop.mobile.dto.Image.InspectionImageUrlRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContractResponseDto {

    private Long id;
    private Long order_id;
    private Long bidding_id;
    private String detail;
    private String shipmentLocation;
    private State state;
    private List<ConstructionImageUrlResponseDto> constructionImageUrlResponseDtos = new ArrayList<>();
    private List<InspectionImageUrlRequestDto> inspectionImageUrlRequestDtos = new ArrayList<>();

    public ContractResponseDto(Contract contract)
    {
        this.id = contract.getId();
        this.order_id = contract.getOrder().getId();
        this.bidding_id = contract.getBidding().getId();
        this.detail = contract.getDetail();
        this.shipmentLocation = contract.getShipmentLocation();
        this.state = contract.getState();
    }

}
