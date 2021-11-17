package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.Contract.CompletedContractRepository;
import com.strongshop.mobile.domain.Contract.Contract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CompletedContractService {

    private final CompletedContractRepository completedContractRepository;

    @Transactional
    public CompletedContract registerCompletedContract (CompletedContract completedContract)
    {
        return completedContractRepository.save(completedContract);
    }

}
