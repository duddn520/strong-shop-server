package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Tinting;

public class TintingRequestDto {

    private String name;
    private Company company;
    private String manufacturer;
    private int price;
    private int stockQuantity;

    //Tinting 속성
    private String color;
    private int irProtectionRate;
    private int uvProtectionRate;
    private int visibleLightTransmittance;

    public Tinting toEntity(){
        return Tinting.builder()
                .name(name)
                .company(company)
                .manufacturer(manufacturer)
                .price(price)
                .stockQuantity(stockQuantity)
                .color(color)
                .irProtectionRate(irProtectionRate)
                .uvProtectionRate(uvProtectionRate)
                .visibleLightTransmittance(visibleLightTransmittance)
                .build();
    }
}
