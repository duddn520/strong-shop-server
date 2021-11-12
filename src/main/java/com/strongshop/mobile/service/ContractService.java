package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    @Transactional
    public Contract getContractByBidding(Bidding bidding)
    {
        Contract contract = contractRepository.findByBidding(bidding)
                .orElseThrow(()->new RuntimeException("해당 계약이 존재하지 않습니다."));

        return contract;
    }
}
