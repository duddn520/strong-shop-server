package com.strongshop.mobile.domain.Bidding;

import com.strongshop.mobile.domain.Company.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
