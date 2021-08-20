package com.strongshop.mobile.dto.Bidding;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BiddingRequestDto {

    private int tintingPrice;
    private int blackboxPrice;
    private int ppfPrice;

    private int totalPrice;
    private LocalDateTime startTime;
    private LocalDateTime dueTime;

    private Order order;
    private Company company;

    public Bidding toEntity(){
        return Bidding.builder().
                tintingPrice(tintingPrice)
                .blackboxPrice(blackboxPrice)
                .ppfPrice(ppfPrice)
                .totalPrice(totalPrice)
                .startTime(startTime)
                .dueTime(dueTime)
                .order(order)
                .company(company)
                .build();
    }
}
