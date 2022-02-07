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

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String filename;
    private String imageUrl;
    private String comment;

    @Builder
    public OrderImage(Order order, String filename, String imageUrl, String comment)
    {
        this.order = order;
        this.filename = filename;
        this.imageUrl = imageUrl;
        this.comment = comment;
    }


}
