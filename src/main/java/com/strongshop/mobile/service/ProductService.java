package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Product.Items.BlackBoxRepository;
import com.strongshop.mobile.domain.Product.Items.PpfRepository;
import com.strongshop.mobile.domain.Product.Items.TintingRepository;
import com.strongshop.mobile.dto.Product.BlackboxRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final TintingRepository tintingRepository;
    private final BlackBoxRepository blackBoxRepository;
    private final PpfRepository ppfRepository;


//    public void registerBlackbox(BlackboxRequestDto blackboxRequestDto){
//
//        blackBoxRepository.save(blackboxRequestDto.toEntity())
//    }

}
