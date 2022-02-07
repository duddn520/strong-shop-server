package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 1500)
    private String detail;
    private String region;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated
    private Kind kind;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<Bidding> biddings = new ArrayList<>();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderImage> orderImages = new ArrayList<>();


    @Builder
    public Order(Long id, User user, String detail,String region,State state,Kind kind, List<Bidding> biddings,List<OrderImage> orderImages)
    {
        this.id = id;
        this.user = user;
        this.detail = detail;
        this.region = region;
        this.state = state;
        this.kind = kind;
        this.biddings = biddings;
        this.orderImages = orderImages;

    }

    public void updateOrder(User user)       //연관관계 편의 메서드.
    {
        this.user = user;
        user.getOrders().add(this);
    }

    public void updateState(State state)
    {
        this.state = state;

    }
}
