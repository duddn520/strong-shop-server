package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Ppf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PpfResponseDto {

    private Long id;
    private String name;
    private Long company_id;
    private String manufacturer;
    private int price;
    private int stockQuantity;

    //ppf 속성

    public PpfResponseDto(Ppf ppf){
        this.id = ppf.getId();
        this.name = ppf.getName();
        this.company_id = ppf.getCompany().getId();
        this.manufacturer = ppf.getManufacturer();
        this.price = ppf.getPrice();
        this.stockQuantity = ppf.getStockQuantity();
    }
}
