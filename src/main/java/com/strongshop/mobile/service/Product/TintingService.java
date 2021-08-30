package com.strongshop.mobile.service.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Product.Items.Blackbox;
import com.strongshop.mobile.domain.Product.Items.Tinting;
import com.strongshop.mobile.domain.Product.Items.TintingRepository;
import com.strongshop.mobile.dto.Product.BlackboxRequestDto;
import com.strongshop.mobile.dto.Product.BlackboxResponseDto;
import com.strongshop.mobile.dto.Product.TintingRequestDto;
import com.strongshop.mobile.dto.Product.TintingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public List<TintingResponseDto> getTintingByCompany(Long company_id)
    {
        Company company = companyRepository.findById(company_id)
                .orElseThrow(()-> new IllegalArgumentException());

        List<Tinting> Tintings = tintingRepository.findAllByCompany(company)
                .orElseThrow(()-> new IllegalArgumentException());

        List<TintingResponseDto> responseDtos = new ArrayList<>();
        for(Tinting b : Tintings)
        {
            responseDtos.add(new TintingResponseDto(b));
        }

        return responseDtos;
    }
}
