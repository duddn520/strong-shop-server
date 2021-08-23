package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.vo.PpfOption;
import com.strongshop.mobile.vo.TintingOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private TintingOption tintingOption;
    private PpfOption ppfOption;
    private User user;
    private Car car;

    private Boolean isBlackBox;
    private Boolean isGlassCoating;
    private Boolean isUnderCoating;
    private Boolean isSoundProof;
    private String request;

    public Order toEntity() {
        return Order.builder()
                .car(car)
                .user(user)
                .tintingOption(tintingOption)
                .ppfOption(ppfOption)
                .isBlackBox(isBlackBox)
                .isGlassCoating(isGlassCoating)
                .isUnderCoating(isUnderCoating)
                .isSoundProof(isSoundProof)
                .build();

    }
}
