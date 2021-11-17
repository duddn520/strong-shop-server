package com.strongshop.mobile.dto.Contract;

import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.Contract.ReviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@NoArgsConstructor
public class CompletedContractResponseDto {

    private Long id;
    private Long user_id;
    private String company_name;
    private String details;
    private String shipment_location;
    private ReviewStatus reviewStatus;

    public CompletedContractResponseDto(CompletedContract completedContract)
    {
        this.id = completedContract.getId();
        this.user_id = completedContract.getUserId();
        this.company_name = completedContract.getCompanyName();
        this.details = completedContract.getDetails();
        this.shipment_location = completedContract.getShipmentLocation();
        this.reviewStatus = completedContract.getReviewStatus();
    }
}
