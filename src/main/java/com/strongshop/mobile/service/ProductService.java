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
    public ProductResponseDto updateProduct(ProductRequestDto requestDto,Company company)
    {
        Product product = productRepository.findById(requestDto.getId())
                .orElseThrow(()-> new RuntimeException());


        return new ProductResponseDto(productRepository.save(product.updateProduct(requestDto.toEntity())));

    }

    @Transactional
    public List<ProductResponseDto> getAllProductsByCompany(Long companyId)
    {
        List<Product> products = productRepository.findAllByCompanyId(companyId)
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
        List<Product> products = productRepository.findAllByItemAndCompanyId(item,companyId)
                .orElseGet(()-> new ArrayList<Product>());

        List<ProductResponseDto> responseDtos = new ArrayList<>();
        for(Product p : products)
        {
            ProductResponseDto res = new ProductResponseDto(p);
            responseDtos.add(res);
        }

        return responseDtos;

    }

    @Transactional
    public Product getProductById(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 제품이 존재하지 않습니다."));
        return product;
    }

    @Transactional
    public void deleteProduct(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 제품을 찾을 수 없습니다."));

        productRepository.delete(product);
    }
}
