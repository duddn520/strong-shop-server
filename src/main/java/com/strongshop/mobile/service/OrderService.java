package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
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
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Transactional
    public OrderResponseDto saveOrder(Order order)
    {
        OrderResponseDto responseDto = new OrderResponseDto(orderRepository.save(order));
        return responseDto;
    }

    @Transactional
    public Order getOrderByOrderId(Long orderId)
    {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("해당 주문이 존재하지 않습니다."));
        return order;
    }

    @Transactional
    public List<Order> getOrdersStateIsBiddingAndSearchedByRegion(String region)
    {
        List<Order> orders = orderRepository.findAllByStateAndRegionOrderByCreatedTimeAsc(State.BIDDING,region)
                .orElseGet(()-> new ArrayList<>());

        return orders;
    }

    @Transactional
    public void updateState2BiddingComplete(Order order)
    {
        order.updateState(State.BIDDING_COMPLETE);
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Order order)
    {
        orderRepository.delete(order);
    }




}
