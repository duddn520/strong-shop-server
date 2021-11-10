package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Order.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class OrderResponseDto {

    private Long id;
    private Long userId;
    private String details;
    private String region;
    private LocalDateTime created_time;

    public OrderResponseDto(Order order)
    {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.details = order.getDetail();
        this.region = order.getRegion();
        this.created_time = order.getCreatedTime();
    }
}
