package com.strongshop.mobile.dto.Order;

import com.strongshop.mobile.domain.Order.OrderImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderImageResponseDto {
    private Long id;
    private String imageUrl;
    private String comment;
    private Long orderId;

    public OrderImageResponseDto (OrderImage orderImage)
    {
        this.id = orderImage.getId();
        this.imageUrl = orderImage.getImageUrl();
        this.comment = orderImage.getComment();
        this.orderId = orderImage.getOrder().getId();
    }
}
