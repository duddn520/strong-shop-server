package com.strongshop.mobile.domain.Contract;

import com.strongshop.mobile.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CompletedContract extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private Long userId;
    private String companyName;
    private String details;             //bidding
    private String shipmentLocation;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    @Builder
    public CompletedContract(Long id,Long userId, String companyName, String details, String shipmentLocation, ReviewStatus reviewStatus)
    {
        this.id = id;
        this.userId = userId;
        this.companyName = companyName;
        this.details = details;
        this.shipmentLocation = shipmentLocation;
        this.reviewStatus = reviewStatus;
    }


}
