package com.strongshop.mobile.service.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Product.Items.Tinting;
import com.strongshop.mobile.domain.Product.Items.TintingRepository;
import com.strongshop.mobile.dto.Product.TintingRequestDto;
import com.strongshop.mobile.dto.Product.TintingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class TintingService {

    private final TintingRepository tintingRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public TintingResponseDto registerTinting(TintingRequestDto requestDto)
    {
        Tinting tinting = requestDto.toEntity();

        Company company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(()-> new IllegalArgumentException());
        tinting.updateCompany(company);
        return new TintingResponseDto(tintingRepository.save(tinting));
    }

    @Transactional
    public TintingResponseDto updateTinting(TintingRequestDto requestDto){
        Tinting tinting = tintingRepository.findById(requestDto.getId())
                .orElseThrow(()->new IllegalArgumentException());

        return new TintingResponseDto(tinting.updateTinting(requestDto.toEntity()));
    }
}
