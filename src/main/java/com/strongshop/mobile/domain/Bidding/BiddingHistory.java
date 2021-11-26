package com.strongshop.mobile.domain.Bidding;

import com.strongshop.mobile.domain.Company.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class BiddingHistory {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(length = 1024)
    private String details;

    @Enumerated(EnumType.STRING)
    private BiddingStatus biddingStatus;

    private LocalDateTime createdTime;

    @Builder
    public BiddingHistory (Long id, Company company, String details, BiddingStatus biddingStatus, LocalDateTime createdTime)
    {
        this.id = id;
        this.company = company;
        this.details = details;
        this.biddingStatus = biddingStatus;
        this.createdTime = createdTime;
    }
}
