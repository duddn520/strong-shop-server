package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.vo.TintingPosition;
import com.strongshop.mobile.vo.TintingStrength;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 여러개의 요청이 하나의 유저
    @JoinColumn(name = "car_id",nullable = false) // 매핑할 외래키 이름지정 - company엔티티의 id필드를 외래키로 갖겠다.
    private Car car;

    // isTinting이 true일때만 null값이 아니게됨
    // jpa에서 entity내부에 컬럼으로 클래스를 추가할때는 @Embedded어노테이션을 사용해야하지만 enum의경우 예외
    @Enumerated(EnumType.STRING) // DB에 저장할때 Enum순서번호가아니라 상수값(문자)그대로 저장해라 TODO - Attribute Converter
    private TintingPosition tintingPosition;
    @Enumerated(EnumType.STRING)
    private TintingStrength tintingStrength;

    private Boolean isTinting;
    private Boolean isBlackBox;
    private Boolean isGlassCoating;
    private Boolean isUnderCoating;
    private Boolean isPdf;
    private Boolean isSoundProof;

    private String request;

    @OneToMany(mappedBy = "order")
    private List<Bidding> biddings = new ArrayList<>();


    @Builder
    public Order(Car car, TintingPosition tintingPosition, TintingStrength tintingStrength, Boolean isTinting, Boolean isBlackBox, Boolean isGlassCoating, Boolean isUnderCoating, Boolean isPdf, Boolean isSoundProof, String request, List<Bidding> biddings) {
        this.car = car;
        this.tintingPosition = tintingPosition;
        this.tintingStrength = tintingStrength;
        this.isTinting = isTinting;
        this.isBlackBox = isBlackBox;
        this.isGlassCoating = isGlassCoating;
        this.isUnderCoating = isUnderCoating;
        this.isPdf = isPdf;
        this.isSoundProof = isSoundProof;
        this.request = request;
        this.biddings = biddings;
    }
}
