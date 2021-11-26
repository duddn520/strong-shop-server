package com.strongshop.mobile.domain.Bidding;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Bidding extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 1024)
    private String detail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private BiddingStatus status;

    @Builder
    public Bidding(Long id,String detail, Order order, Company company, BiddingStatus status)
    {
        this.id = id;
        this.detail = detail;
        this.order = order;
        this.company = company;
        this.status = status;
    }

    public void updateCompanyBidding(Company company)        //bidding register시 필수 실행.
    {
        company.getBiddings().add(this);
    }

    public void updateStatus(BiddingStatus status)
    {
        this.status = status;
    }

}
