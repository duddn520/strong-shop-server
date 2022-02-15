package com.strongshop.mobile.domain.Order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderImage {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;

    private String filename;
    private String imageUrl;
    private String comment;

    @Builder
    public OrderImage(Long id,Order order, String filename, String imageUrl, String comment)
    {
        this.id = id;
        this.order = order;
        this.filename = filename;
        this.imageUrl = imageUrl;
        this.comment = comment;
    }

    public void updateOrder(Order order)
    {
        this.order = order;
    }


}
