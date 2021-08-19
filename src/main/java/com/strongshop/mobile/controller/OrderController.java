package com.strongshop.mobile.controller;


import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.dto.order.OrderRequestDto;
import com.strongshop.mobile.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public void requestOrder(@RequestBody OrderRequestDto requestDto, Authentication authentication) {
        Order order = orderService.saveOrder(requestDto, authentication);
    }
}