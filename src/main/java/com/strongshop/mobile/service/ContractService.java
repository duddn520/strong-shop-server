package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Contract.ContractRepository;
import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import com.strongshop.mobile.domain.Image.ConstructionImageUrlRepository;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.domain.Image.InspectionImageUrlRepository;
import com.strongshop.mobile.domain.Order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final InspectionImageUrlRepository inspectionImageUrlRepository;
    private final ConstructionImageUrlRepository constructionImageUrlRepository;

    @Transactional
    public Contract registerContract(Contract contract)
    {
        return contractRepository.save(contract);
    }

    @Transactional
    public Contract getContractByBidding(Bidding bidding)
    {
        Contract contract = contractRepository.findByBidding(bidding)
                .orElseThrow(()->new RuntimeException("해당 계약이 존재하지 않습니다."));

        return contract;
    }

    @Transactional
    public Contract getContractByOrder(Order order)
    {
        Contract contract = contractRepository.findByOrder(order)
                .orElseThrow(()->new RuntimeException("해당 계약이 존재하지 않습니다."));
        return contract;
    }

    @Transactional
    public Contract getContractById(Long contract_Id)
    {
        Contract contract = contractRepository.findById(contract_Id)
                .orElseThrow(()->new RuntimeException(("해당 계약이 존재하지 않습니다.")));
        return contract;
    }

    @Transactional
    public void deleteContract(Contract contract)
    {
        contractRepository.delete(contract);
    }

}
