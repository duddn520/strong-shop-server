package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Contract.ContractRepository;
import com.strongshop.mobile.domain.Order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

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
    public List<Contract> getContractsByCompanyId(Long id){
        List<Contract> contracts = contractRepository.findAllByCompanyId(id)
                .orElseGet(()->new ArrayList<>());
        return contracts;
    }

    @Transactional
    public List<Contract> getContractsByUserId(Long id){
        List<Contract> contracts = contractRepository.findAllByUserId(id)
                .orElseGet(()->new ArrayList<>());
        return contracts;
    }

    @Transactional
    public void deleteContract(Contract contract)
    {
        contractRepository.delete(contract);
    }

}
