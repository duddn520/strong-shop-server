package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Bidding.BiddingRepository;
import com.strongshop.mobile.domain.Bidding.BiddingStatus;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.dto.Bidding.BiddingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BiddingService {

    private final BiddingRepository biddingRepository;
    private final OrderService orderService;


    @Transactional
    public Bidding registerBidding(BiddingRequestDto requestDto, Order order, Company company){
        Bidding bidding = Bidding.builder()
                .detail(requestDto.getDetail())
                .company(company)
                .order(order)
                .status(BiddingStatus.ONGOING)
                .build();

        bidding.updateCompanyBidding(company);
        return biddingRepository.save(bidding);
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

    @Transactional
    public Bidding getBiddingByBiddingId(Long biddingId)
    {
        Bidding bidding = biddingRepository.findById(biddingId)
                .orElseThrow(()->new RuntimeException("해당 입찰이 존재하지 않습니다."));

        return bidding;
    }

    @Transactional
    public List<Bidding> getAllBiddingsInSuccessAndCompany(Company company)
    {
        List<Bidding> biddings = biddingRepository.findAllByStatusAndCompany(BiddingStatus.SUCCESS,company)
                .orElseGet(()-> new ArrayList<>());
        return biddings;
    }

    @Transactional
    public void deleteBidding(Bidding bidding)
    {
        biddingRepository.delete(bidding);
    }
}
