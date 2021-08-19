package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Order.Orders;
import com.strongshop.mobile.domain.Order.OrdersRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.order.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserService userService;
    private final OrdersRepository ordersRepository;

    @Transactional
    public Orders saveOrder(OrderRequestDto requestDto, Authentication authentication) {
        User user = userService.getUserByToken(authentication.getPrincipal());
        requestDto.setUser(user);
        if (requestDto.getIsTinting()) {
            //TODO - OrderResponsetDto로 리턴
            return ordersRepository.save(requestDto.toEntityWithTinting());
        } else {
            return ordersRepository.save(requestDto.toEntityWithoutTinting());
        }
    }
}
