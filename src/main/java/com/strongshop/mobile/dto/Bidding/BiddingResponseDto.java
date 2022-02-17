package com.strongshop.mobile.dto.Bidding;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Order.Kind;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BiddingResponseDto {

    private Long id;
    private String detail;
    private Long order_id;
    private Long company_id;
    private BiddingStatus status;
    private String address;
    private String company_name;
    private String companyImage;
    private Kind kind;

    public BiddingResponseDto(Bidding bidding){
        this.id = bidding.getId();
        this.detail = bidding.getDetail();
        this.order_id = bidding.getOrder().getId();
        this.company_id = bidding.getCompany().getId();
        this.status = bidding.getStatus();
        this.address = bidding.getCompany().getCompanyInfo().getAddress();
        this.company_name = bidding.getCompany().getName();
        this.companyImage = bidding.getCompany().getCompanyInfo().getBackgroundImageUrl();
        this.kind = bidding.getOrder().getKind();
    }
}
