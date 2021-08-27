package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.vo.PpfOption;
import com.strongshop.mobile.vo.TintingOption;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderResponseDto {

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

//    @Builder
//    public OrderResponseDto(Order order) {
//        this.tintingOption = order.getTintingOption();
//        this.ppfOption = order.getPpfOption();
//        this.user = order.getUser(); // TODO - 여기서 user를 보내냐 userID를 보내냐는 클라화면에서 한번더 들어가서 사용자정보를 보여주는지 바로보여줄지에 따라 다를듯
//        this.car = order.getCar();
//        this.isBlackBox = order.getIsBlackBox();
//        this.isGlassCoating = order.getIsGlassCoating();
//        this.isUnderCoating = order.getIsUnderCoating();
//        this.isSoundProof = order.getIsSoundProof();
//        this.request = order.getRequest();
//    }

    @Builder
    public OrderResponseDto(TintingOption tintingOption, PpfOption ppfOption, User user, Car car, Boolean isBlackBox, Boolean isGlassCoating, Boolean isUnderCoating, Boolean isSoundProof, String request) {
        this.tintingOption = tintingOption;
        this.ppfOption = ppfOption;
        this.user = user;
        this.car = car;
        this.isBlackBox = isBlackBox;
        this.isGlassCoating = isGlassCoating;
        this.isUnderCoating = isUnderCoating;
        this.isSoundProof = isSoundProof;
        this.request = request;
    }
}
