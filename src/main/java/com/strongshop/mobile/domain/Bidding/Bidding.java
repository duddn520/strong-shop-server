package com.strongshop.mobile.domain.Bidding;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
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

    public Bidding updateBidding(Bidding bidding){
        this.tintingPrice = bidding.getTintingPrice();
        this.blackboxPrice = bidding.getBlackboxPrice();
        this.ppfPrice = bidding.getPpfPrice();
        this.totalPrice = bidding.getTotalPrice();

        return this;
    }

}
