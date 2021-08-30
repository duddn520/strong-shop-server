package com.strongshop.mobile.service.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Product.Items.Ppf;
import com.strongshop.mobile.domain.Product.Items.PpfRepository;
import com.strongshop.mobile.dto.Product.PpfRequestDto;
import com.strongshop.mobile.dto.Product.PpfResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PpfService {

    private final PpfRepository ppfRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public PpfResponseDto registerPpf(PpfRequestDto requestDto){
       Ppf ppf = requestDto.toEntity();

        Company company = companyRepository.findById(requestDto.getCompanyId())
                .orElseThrow(()-> new IllegalArgumentException());
        ppf.updateCompany(company);
        return new PpfResponseDto(ppfRepository.save(ppf));
    }

    @Transactional
    public PpfResponseDto updatePpf(PpfRequestDto requestDto){
        System.out.println("request id :"+requestDto.getId());
        Ppf ppf = ppfRepository.findById(requestDto.getId())
                .orElseThrow(()-> new IllegalArgumentException());
        return new PpfResponseDto(ppf.updatePpf(requestDto.toEntity()));

    }

    @Transactional
    public List<PpfResponseDto> getPpfByCompany(Long company_id)
    {
        Company company = companyRepository.findById(company_id)
                .orElseThrow(()-> new IllegalArgumentException());

        List<Ppf> Ppfs = ppfRepository.findAllByCompany(company)
                .orElseThrow(()-> new IllegalArgumentException());

        List<PpfResponseDto> responseDtos = new ArrayList<>();
        for(Ppf b : Ppfs)
        {
            responseDtos.add(new PpfResponseDto(b));
        }

        return responseDtos;
    }
}
