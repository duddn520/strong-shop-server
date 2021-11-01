package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(length = 1024)
    private String detail;

    private String region;

    @Builder
    public Order(Long id, User user, String detail,String region)
    {
        this.id = id;
        this.user = user;
        this.detail = detail;
        this.region = region;
    }

    public Order updateOrder(User user, String detail, String region)
    {
        this.user = user;
        this.detail = detail;
        this.region = region;
        return this;
    }
}
