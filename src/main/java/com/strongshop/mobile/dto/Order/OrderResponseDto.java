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

    private Long id;
    private Long userId;
    private String details;
    private String region;

    public OrderResponseDto(Order order)
    {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.details = order.getDetail();
        this.region = order.getRegion();
    }
}
