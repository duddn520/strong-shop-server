package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.Contract.CompletedContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletedContractService {

    private final CompletedContractRepository completedContractRepository;

    @Transactional
    public CompletedContract registerCompletedContract (CompletedContract completedContract)
    {
        return completedContractRepository.save(completedContract);
    }

    @Transactional
    public List<CompletedContract> findAllHistoriesByUserId(Long userId)
    {
        List<CompletedContract> completedContracts = completedContractRepository.findAllByUserId(userId)
                .orElseGet(()->new ArrayList<>());

        return completedContracts;
    }

    @Transactional
    public CompletedContract getCompletedContractById(Long id)
    {
        CompletedContract contract = completedContractRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 거래내역이 존재하지 않습니다."));

        return contract;
    }

}
