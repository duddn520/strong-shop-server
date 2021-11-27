package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class OrderResponseDto {

    private Long id;
    private Long userId;
    private String details;
    private String region;
    private State state;
    private List<Bidding> biddings = new ArrayList<>();
    private int bidcount;
    private LocalDateTime created_time;

    public OrderResponseDto(Order order)
    {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.details = order.getDetail();
        this.region = order.getRegion();
        this.state = order.getState();
        this.biddings = order.getBiddings();
        if(biddings.isEmpty())
        {
            this.bidcount = 0;
        }
        else
            this.bidcount = order.getBiddings().size();
        this.created_time = order.getCreatedTime();
    }
}
