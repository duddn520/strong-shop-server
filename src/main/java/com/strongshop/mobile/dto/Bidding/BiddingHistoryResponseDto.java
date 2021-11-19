package com.strongshop.mobile.dto.Bidding;

import com.strongshop.mobile.domain.Bidding.BiddingHistory;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class BiddingHistoryResponseDto {

    private Long id;
    private String details;
    private BiddingStatus biddingStatus;
    private LocalDateTime createdTime;

    public BiddingHistoryResponseDto(BiddingHistory biddingHistory)
    {
        this.id = biddingHistory.getId();
        this.details = biddingHistory.getDetails();
        this.biddingStatus = biddingHistory.getBiddingStatus();
        this.createdTime = biddingHistory.getCreatedTime();
    }

}
