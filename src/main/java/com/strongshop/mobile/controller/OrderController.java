package com.strongshop.mobile.controller;


import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.dto.Order.OrderRequestDto;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import com.strongshop.mobile.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public void requestOrder(@RequestBody OrderRequestDto requestDto, Authentication authentication) {
        Order order = orderService.saveOrder(requestDto, authentication);
        log.info(order.toString());
    }

    @PostMapping("/api/orders/test")
    public void requestOrder(@RequestBody OrderRequestDto requestDto) {
        Order order = orderService.saveOrderTest(requestDto);
        log.info(order.toString());
    }

//    @GetMapping("/api/user/orders")
//    public void getOrder(Authentication authentication) {
//        Order order = orderService.saveOrderTest(requestDto);
//        log.info(order.toString());
//    }

    @GetMapping("/api/user/orders")
    public void getOrder() {
        OrderResponseDto responseDto = orderService.getOrderForUserTest();
        log.info(responseDto.getTintingOption().getPositionFront().toString());
        log.info(responseDto.getTintingOption().getPositionFront().toString());
    }

    @GetMapping("/api/company/orders")
    public void getOrder(@RequestBody OrderRequestDto requestDto ) {
        Order order = orderService.saveOrderTest(requestDto);
        log.info(order.toString());
    }


}