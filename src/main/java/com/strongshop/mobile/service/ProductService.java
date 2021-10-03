package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Product.Item;
import com.strongshop.mobile.domain.Product.Product;
import com.strongshop.mobile.domain.Product.ProductRepository;
import com.strongshop.mobile.dto.Product.ProductRequestDto;
import com.strongshop.mobile.dto.Product.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public ProductResponseDto registerProduct(ProductRequestDto requestDto)
    {
        Product product = requestDto.toEntity();
        return new ProductResponseDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProduct(ProductRequestDto requestDto)
    {
        Product product = productRepository.findById(requestDto.getId())
                .orElseThrow(()-> new RuntimeException());

        return new ProductResponseDto(productRepository.save(product.updateProduct(requestDto.toEntity())));

    }

    @Transactional
    public List<ProductResponseDto> getAllProductsByCompany(Long companyId)
    {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new RuntimeException());
        List<Product> products = productRepository.findAllByCompany(company)
                .orElseThrow(()-> new RuntimeException());

        List<ProductResponseDto> responseDtos = new ArrayList<>();
        for(Product p : products)
        {
            ProductResponseDto res = new ProductResponseDto(p);
            responseDtos.add(res);
        }

        return responseDtos;
    }

    @Transactional
    public List<ProductResponseDto> getSpecificItemsByCompany(Long companyId, Item item)
    {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new RuntimeException());
        List<Product> products = productRepository.findAllByItemAndCompany(item,company)
                .orElseThrow(()-> new RuntimeException());

        List<ProductResponseDto> responseDtos = new ArrayList<>();
        for(Product p : products)
        {
            ProductResponseDto res = new ProductResponseDto(p);
            responseDtos.add(res);
        }

        return responseDtos;

    }
}
