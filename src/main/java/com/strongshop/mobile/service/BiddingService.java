package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingRepository;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.dto.Bidding.BiddingRequestDto;
import com.strongshop.mobile.dto.Bidding.BiddingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BiddingService {

    private final BiddingRepository biddingRepository;

    @Transactional
    public BiddingResponseDto registerBidding(BiddingRequestDto requestDto){
        Bidding bidding = requestDto.toEntity();
        return new BiddingResponseDto(biddingRepository.save(bidding));
    }

    @Transactional
    public List<Bidding> findbyOrder(Order order)
    {
        return biddingRepository.findAllByOrder(order);
    }
}
