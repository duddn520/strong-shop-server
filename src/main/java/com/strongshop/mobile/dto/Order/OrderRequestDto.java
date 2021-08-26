package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.vo.PpfOption;
import com.strongshop.mobile.vo.TintingOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private TintingOption tintingOption;
    private PpfOption ppfOption;
    private User user;
    private Long carId;
    private Car car;

    private Boolean isBlackBox;
    private Boolean isGlassCoating;
    private Boolean isUnderCoating;
    private Boolean isSoundProof;
    private String request;

    // carId는 그냥 사용자로부터 정보 받으려고 필드에 포함시킨것이고 엔티티로 만들땐 사용되지않음
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
