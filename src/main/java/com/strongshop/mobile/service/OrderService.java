package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.Order.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;

    @Transactional
    public Order saveOrder(OrderRequestDto requestDto, Authentication authentication) {
        User user = userService.getUserByToken(authentication.getPrincipal());
        requestDto.setUser(user);
        if (requestDto.getIsTinting()) {
            //TODO - OrderResponsetDto로 리턴
            return orderRepository.save(requestDto.toEntityWithTinting());
        } else {
            return orderRepository.save(requestDto.toEntityWithoutTinting());
        }
    }
}
