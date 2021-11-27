package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class OrderResponseDto {

    private Long id;
    private Long userId;
    private String details;
    private String region;
    private State state;
    private int bidcount;
    private LocalDateTime created_time;

    public OrderResponseDto(Order order)
    {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.details = order.getDetail();
        this.region = order.getRegion();
        this.state = order.getState();
        if(order.getBiddings().isEmpty())
        {
            this.bidcount = 0;
        }
        else
            this.bidcount = order.getBiddings().size();
        this.created_time = order.getCreatedTime();
    }
}
