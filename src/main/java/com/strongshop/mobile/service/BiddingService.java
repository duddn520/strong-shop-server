package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingRepository;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.dto.Bidding.BiddingRequestDto;
import com.strongshop.mobile.dto.Bidding.BiddingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BiddingService {

    private final BiddingRepository biddingRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;


    @Transactional
    public BiddingResponseDto registerBidding(BiddingRequestDto requestDto, Company company){
        Bidding bidding = Bidding.builder()
                .detail(requestDto.getDetail())
                .company(company)
                .status(BiddingStatus.ONGOING)
                .build();
        Order order = orderRepository.findById(requestDto.getOrder_id())
                .orElseThrow(()-> new RuntimeException("해당 주문이 존재하지 않습니다."));
        if (order.getState().equals(State.BIDDING)) {
            bidding.updateBiddingAndOrderAndCompany(order, company);
            biddingRepository.save(bidding);
            return new BiddingResponseDto(bidding);
        }
        else
            throw new RuntimeException("입찰 진행중인 주문에 한해서만 입찰 가능합니다.");
    }

    @Transactional
    public List<Bidding> getAllBiddingsByOrderId(Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        List<Bidding> biddings = biddingRepository.findAllByOrder(order)
                .orElseGet(()->new ArrayList<>());

        return biddings;
    }

    @Transactional
    public List<Bidding> getAllBiddingsByCompany(Company company)
    {
        List<Bidding> biddings = biddingRepository.findAllByCompany(company)
                .orElseGet(()->new ArrayList<>());
        return biddings;
    }
}
