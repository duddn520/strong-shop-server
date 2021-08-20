package com.strongshop.mobile.dto.Bidding;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BiddingResponseDto {

    private Long id;
    private int tintingPrice;
    private int blackboxPrice;
    private int ppfPrice;
    private int totalPrice;
    private LocalDateTime startTime;
    private LocalDateTime dueTime;
    private Order order;
    private Company company;

    public BiddingResponseDto(Bidding bidding){
        this.id = bidding.getId();
        this.tintingPrice = bidding.getTintingPrice();
        this.blackboxPrice = bidding.getBlackboxPrice();
        this.ppfPrice = bidding.getPpfPrice();
        this.totalPrice = bidding.getTotalPrice();
        this.startTime = bidding.getStartTime();
        this.dueTime = bidding.getDueTime();
        this.order = bidding.getOrder();
        this.company = bidding.getCompany();
    }
}
