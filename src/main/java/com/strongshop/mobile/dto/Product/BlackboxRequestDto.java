package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Blackbox;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class BlackboxRequestDto {

    private String name;
    private Company company;
    private String manufacturer;
    private int price;
    private int stockQuantity;
    //Blackbox 속성
    private String duration;
    private String resolution;



    public Blackbox toEntity(){
        return Blackbox.builder()
                .name(name)
                .company(company)
                .manufacturer(manufacturer)
                .price(price)
                .stockQuantity(stockQuantity)
                .duration(duration)
                .resolution(resolution)
                .build();
    }
}
