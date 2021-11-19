package com.strongshop.mobile.dto.Contract;

import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.Contract.ReviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class CompletedContractResponseDto {

    private Long id;
    private Long user_id;
    private Long company_id;
    private String company_name;
    private String company_thumbnail_image;
    private String details;
    private String shipment_location;
    private ReviewStatus reviewStatus;
    private LocalDateTime createdtime;

    public CompletedContractResponseDto(CompletedContract completedContract)
    {
        this.id = completedContract.getId();
        this.user_id = completedContract.getUser().getId();
        this.company_id = completedContract.getCompany().getId();
        this.company_name = completedContract.getCompany().getName();
        this.company_thumbnail_image = completedContract.getCompany().getCompanyInfo().getBackgroundImageUrl();
        this.details = completedContract.getDetails();
        this.shipment_location = completedContract.getShipmentLocation();
        this.reviewStatus = completedContract.getReviewStatus();
        this.createdtime = completedContract.getCreatedTime();
    }
}
