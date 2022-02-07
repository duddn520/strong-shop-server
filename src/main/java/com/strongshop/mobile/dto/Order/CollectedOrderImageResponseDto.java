package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderImage;
import com.strongshop.mobile.dto.Order.OrderImageResponseDto;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CollectedOrderImageResponseDto {

    private Long id;
    private List<OrderImageResponseDto> responseDtos = new ArrayList<>();

    public CollectedOrderImageResponseDto(Order order)
    {
        this.id = order.getId();
        this.responseDtos = image2ResponseDtos(order);
    }

    @Transactional
    public List<OrderImageResponseDto> image2ResponseDtos(Order order)
    {
        List<OrderImage> orderImages = order.getOrderImages();
        List<OrderImageResponseDto> responseDtos = new ArrayList<>();

        for(OrderImage o : orderImages)
        {
            OrderImageResponseDto responseDto = new OrderImageResponseDto(o);
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}
