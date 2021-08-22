package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Ppf;

public class PpfRequestDto {

    private String name;
    private Company company;
    private String manufacturer;
    private int price;
    private int stockQuantity;
    //ppf 속성


    public Ppf toEntity(){
        return Ppf.builder()
                .name(name)
                .company(company)
                .manufacturer(manufacturer)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

}
