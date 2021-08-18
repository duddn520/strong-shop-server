package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.order.OrderRequestDto;
import com.strongshop.mobile.vo.Tinting;
import com.strongshop.mobile.vo.TintingPosition;
import com.strongshop.mobile.vo.TintingStrength;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 여러개의 요청이 하나의 유저
    private User user;

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

    @Builder
    public Order(User user, TintingPosition tintingPosition, TintingStrength tintingStrength, Boolean isTinting, Boolean isBlackBox, Boolean isGlassCoating, Boolean isUnderCoating, Boolean isPdf, Boolean isSoundProof, String request) {
        this.user = user;
        this.tintingPosition = tintingPosition;
        this.tintingStrength = tintingStrength;
        this.isTinting = isTinting;
        this.isBlackBox = isBlackBox;
        this.isGlassCoating = isGlassCoating;
        this.isUnderCoating = isUnderCoating;
        this.isPdf = isPdf;
        this.isSoundProof = isSoundProof;
        this.request = request;
    }
}
