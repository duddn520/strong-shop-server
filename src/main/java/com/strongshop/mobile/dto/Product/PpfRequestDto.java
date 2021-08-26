package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Ppf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PpfRequestDto {

    private Long id;
    private String name;
    private Long companyId;
    private String manufacturer;
    private int price;
    private int stockQuantity;
    //ppf 속성


    public Ppf toEntity(){
        return Ppf.builder()
                .name(name)
                .manufacturer(manufacturer)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

}
