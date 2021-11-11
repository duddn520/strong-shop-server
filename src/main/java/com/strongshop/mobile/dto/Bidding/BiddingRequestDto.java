package com.strongshop.mobile.dto.Bidding;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class BiddingRequestDto {

    private Long id;
    private String detail;
    private Long order_id;
    private BiddingStatus status;

    public Bidding toEntity(){
        return Bidding.builder()
                .detail(detail)
                .status(status)
                .build();
    }
}
