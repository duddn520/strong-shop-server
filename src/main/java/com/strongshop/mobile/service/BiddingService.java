package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingRepository;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.dto.Bidding.BiddingRequestDto;
import com.strongshop.mobile.dto.Bidding.BiddingResponseDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.pool.TypePool;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BiddingService {

    private final BiddingRepository biddingRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;


    @Transactional
    public BiddingResponseDto registerBidding(BiddingRequestDto requestDto,Company company){
        Bidding bidding = new Bidding();
        Order order = orderRepository.findById(requestDto.getOrder_id())
                .orElseThrow(()-> new RuntimeException("해당 주문이 존재하지 않습니다."));
        bidding.updateBiddingAndOrderAndCompany(order,company);
        company.updateBiddedOrders(order);
        biddingRepository.save(bidding);
        return new BiddingResponseDto(bidding);
    }

    @Transactional
    public List<Bidding> getAllBiddingsByOrderId(Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        List<Bidding> biddings = biddingRepository.findAllByOrder(order)
                .orElseGet(()->new ArrayList<>());

        return biddings;
    }
}
