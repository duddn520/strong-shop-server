package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.vo.TintingPosition;
import com.strongshop.mobile.vo.TintingStrength;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private TintingPosition tintingPosition;
    private TintingStrength tintingStrength;

    private Car car;
    private Boolean isTinting;
    private Boolean isBlackBox;
    private Boolean isGlassCoating;
    private Boolean isUnderCoating;
    private Boolean isPdf;
    private Boolean isSoundProof;

    private String request;

    public Order toEntityWithTinting() {
        return Order.builder()
                .car(car)
                .isTinting(isTinting)
                .tintingPosition(tintingPosition)
                .tintingStrength(tintingStrength)
                .isBlackBox(isBlackBox)
                .isGlassCoating(isGlassCoating)
                .isUnderCoating(isUnderCoating)
                .isPdf(isPdf)
                .isSoundProof(isSoundProof)
                .request(request)
                .build();
    }

    public Order toEntityWithoutTinting() {
        return Order.builder()
                .car(car)
                .isTinting(isTinting)
                .isBlackBox(isBlackBox)
                .isGlassCoating(isGlassCoating)
                .isUnderCoating(isUnderCoating)
                .isPdf(isPdf)
                .isSoundProof(isSoundProof)
                .request(request)
                .build();
    }

}
