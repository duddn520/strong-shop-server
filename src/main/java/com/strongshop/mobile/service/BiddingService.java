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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BiddingService {

    private final BiddingRepository biddingRepository;
    private final CompanyRepository companyRepository;
    private final OrderRepository orderRepository;


    @Transactional
    public BiddingResponseDto registerBidding(BiddingRequestDto requestDto){
        Bidding bidding = requestDto.toEntity();
        Order order = orderRepository.findById(requestDto.getOrder_pk())
                .orElseThrow(()-> new IllegalArgumentException());

        Company company = companyRepository.findById(requestDto.getCompany_pk())
                .orElseThrow(()-> new IllegalArgumentException());

        bidding.updateOrderCompany(order,company);

        return new BiddingResponseDto(biddingRepository.save(bidding));
    }

    @Transactional
    public List<Bidding> findbyOrder(Order order)
    {
        return biddingRepository.findAllByOrder(order);
    }
}
