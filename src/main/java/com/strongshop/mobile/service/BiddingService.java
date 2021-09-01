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

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        Order order = orderRepository.findById(requestDto.getOrder_id())
                .orElseThrow(()-> new IllegalArgumentException());

        Company company = companyRepository.findById(requestDto.getCompany_id())
                .orElseThrow(()-> new IllegalArgumentException());

        bidding.updateOrderCompany(order,company);

        return new BiddingResponseDto(biddingRepository.save(bidding));
    }

    @Transactional
    public BiddingResponseDto updateBidding(BiddingRequestDto requestDto){
        Bidding bidding = biddingRepository.findById(requestDto.getId())
                .orElseThrow(()-> new IllegalArgumentException());

        return new BiddingResponseDto(bidding.updateBidding(requestDto.toEntity()));
    }

    @Transactional
    public List<BiddingResponseDto> getBiddingsByCompany(String companyName){
        Company company = companyRepository.findByName(companyName)
                .orElseThrow(()-> new IllegalArgumentException());

        List<Bidding> biddings = biddingRepository.findAllByCompany(company)
                .orElseThrow(()-> new IllegalArgumentException());

        List<BiddingResponseDto> responseDtos = new ArrayList<>();
        for(Bidding b : biddings) {
            responseDtos.add(new BiddingResponseDto(b));
        }

        return responseDtos;
    }
}
