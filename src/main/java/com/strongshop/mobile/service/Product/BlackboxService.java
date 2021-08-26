package com.strongshop.mobile.service.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Product.Items.BlackBoxRepository;
import com.strongshop.mobile.domain.Product.Items.Blackbox;
import com.strongshop.mobile.dto.Product.BlackboxRequestDto;
import com.strongshop.mobile.dto.Product.BlackboxResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BlackboxService {

    private final BlackBoxRepository blackBoxRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public BlackboxResponseDto registerBlackbox(BlackboxRequestDto requestDto){
        Blackbox blackbox = requestDto.toEntity();

        Company company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(()-> new IllegalArgumentException());
        blackbox.updateCompany(company);
        return new BlackboxResponseDto(blackBoxRepository.save(blackbox));
    }

    @Transactional
    public BlackboxResponseDto updateBlackbox(BlackboxRequestDto requestDto){
        Blackbox blackbox = blackBoxRepository.findById(requestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException());
        return new BlackboxResponseDto(blackbox.updateBlackbox(requestDto.toEntity()));
    }
}
