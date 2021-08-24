package com.strongshop.mobile.dto.Bidding;

import com.strongshop.mobile.domain.Bidding.Bidding;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class BiddingRequestDto {

    private int tintingPrice;
    private int blackboxPrice;
    private int ppfPrice;

    private int totalPrice;

    private Long order_pk;
    private Long company_pk;


    public Bidding toEntity(){
        return Bidding.builder()
                .tintingPrice(tintingPrice)
                .blackboxPrice(blackboxPrice)
                .ppfPrice(ppfPrice)
                .totalPrice(totalPrice)
                .build();
    }
}
