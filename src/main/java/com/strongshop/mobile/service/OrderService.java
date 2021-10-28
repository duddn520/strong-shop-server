package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Car.CarRepository;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.Order.OrderRequestDto;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import com.strongshop.mobile.vo.TintingOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final TintingOptionRepository tintingOptionRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Transactional
    public OrderResponseDto saveOrder(Order order)
    {
        OrderResponseDto responseDto = new OrderResponseDto(orderRepository.save(order));
        return responseDto;
    }



}
