package com.strongshop.mobile.dto.order;

import com.strongshop.mobile.domain.Order.Orders;
import com.strongshop.mobile.domain.User.User;
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

    private User user;
    private Boolean isTinting;
    private Boolean isBlackBox;
    private Boolean isGlassCoating;
    private Boolean isUnderCoating;
    private Boolean isPdf;
    private Boolean isSoundProof;

    private String request;

    public Orders toEntityWithTinting() {
        return Orders.builder()
                .user(user)
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

    public Orders toEntityWithoutTinting() {
        return Orders.builder()
                .user(user)
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
