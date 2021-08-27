package com.strongshop.mobile.domain.Bidding;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Getter
public class Bidding {

    @Id @GeneratedValue
    private Long id;

    private int tintingPrice;
    private int blackboxPrice;
    private int ppfPrice;
    private int totalPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public Bidding(int tintingPrice, int blackboxPrice, int ppfPrice, int totalPrice, Order order, Company company)
    {
        this.tintingPrice = tintingPrice;
        this.blackboxPrice = blackboxPrice;
        this.ppfPrice = ppfPrice;
        this.totalPrice = totalPrice;
        this.order = order;
        this.company = company;
    }

    public void updateOrderCompany(Order order, Company company){
        this.company = company;
        this.order = order;
    }

}
