package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Order.Kind;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderImage;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.domain.User.Role;
import io.grpc.Grpc;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.agent.builder.AgentBuilder;

import javax.transaction.Transactional;
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
    private Kind kind;
    private List<OrderImageResponseDto> responseDtos;
    private int bidcount;
    private Role role;
    private LocalDateTime created_time;

    public OrderResponseDto(Order order)
    {
        this.id = order.getId();
        this.userId = order.getUser().getId();
        this.details = order.getDetail();
        this.region = order.getRegion();
        this.state = order.getState();
        this.kind = order.getKind();
        this.responseDtos = orderImage2ResponseDtos(order.getOrderImages());
        this.role = order.getUser().getRole();
        this.created_time = order.getCreatedTime();
    }

    public List<OrderImageResponseDto> orderImage2ResponseDtos(List<OrderImage> orderImages)
    {
        List<OrderImageResponseDto> responseDtos = new ArrayList<>();
        for(OrderImage o : orderImages)
        {
            OrderImageResponseDto responseDto = new OrderImageResponseDto(o);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}
