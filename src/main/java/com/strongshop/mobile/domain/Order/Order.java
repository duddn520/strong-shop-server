package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.vo.PpfOption;
import com.strongshop.mobile.vo.TintingOption;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY) // 여러개의 요청이 하나의 카
    @JoinColumn(nullable = false) // 매핑할 외래키 이름지정 - company엔티티의 id필드를 외래키로 갖겠다.
    private Car car;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private TintingOption tintingOption;

    @OneToOne(fetch = FetchType.LAZY)
    private PpfOption ppfOption;

    private Boolean isBlackBox;
    private Boolean isGlassCoating;
    private Boolean isUnderCoating;
    private Boolean isSoundProof;
    private String request;

    @Builder
    public Order(Car car, User user, TintingOption tintingOption, PpfOption ppfOption, Boolean isBlackBox, Boolean isGlassCoating, Boolean isUnderCoating, Boolean isSoundProof, String request) {
        this.car = car;
        this.user = user;
        this.tintingOption = tintingOption;
        this.ppfOption = ppfOption;
        this.isBlackBox = isBlackBox;
        this.isGlassCoating = isGlassCoating;
        this.isUnderCoating = isUnderCoating;
        this.isSoundProof = isSoundProof;
        this.request = request;
    }
}
