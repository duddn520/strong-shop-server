package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Order.Order;
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
