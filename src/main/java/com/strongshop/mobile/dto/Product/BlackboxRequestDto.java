package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Blackbox;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class BlackboxRequestDto {

    private Long id;
    private String name;
    private Long companyId;
    private String manufacturer;
    private int price;
    private int stockQuantity;
    //Blackbox 속성
    private String duration;
    private String resolution;



    public Blackbox toEntity(){
        return Blackbox.builder()
                .name(name)
                .manufacturer(manufacturer)
                .price(price)
                .stockQuantity(stockQuantity)
                .duration(duration)
                .resolution(resolution)
                .build();
    }
}
