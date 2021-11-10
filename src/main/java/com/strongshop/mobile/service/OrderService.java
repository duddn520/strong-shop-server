package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.domain.Order.State;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto saveOrder(Order order)
    {
        OrderResponseDto responseDto = new OrderResponseDto(orderRepository.save(order));
        return responseDto;
    }

    @Transactional
    public List<Order> getOrdersByRegion(String region)
    {
        List<Order> orders = orderRepository.findAllByRegionOrderByCreatedTimeAsc(region)
                .orElseGet(()->new ArrayList<>());

        return orders;
    }

    @Transactional
    public List<Order> getOrdersStateIsBidding()
    {
        List<Order> orders = orderRepository.findAllByStateOrderByCreatedTimeAsc(State.BIDDING)
                .orElseGet(()-> new ArrayList<>());

        return orders;
    }

    @Transactional
    public List<Order> getOrdersByUser(User user)
    {
        List<Order> orders = orderRepository.findAllByUser(user)
                .orElseGet(()->new ArrayList<>());

        return orders;
    }

    @Transactional
    public void updateState2BiddingComplete(Order order)
    {
        order.updateState(State.BIDDING_COMPLETE);
        orderRepository.save(order);
    }




}
