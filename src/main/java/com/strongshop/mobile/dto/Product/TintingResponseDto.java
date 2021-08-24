package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Tinting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TintingResponseDto {

    private Long id;
    private String name;
    private Company company;
    private String manufacturer;
    private int price;
    private int stockQuantity;

    //tinting속성
    private int uvProtectionRate;
    private int irProtectionRate;
    private int visibleLightTransmittance;
    private String color;

    public TintingResponseDto(Tinting tinting)
    {
        this.id = tinting.getId();
        this.name = tinting.getName();
        this.company = tinting.getCompany();
        this.manufacturer = tinting.getManufacturer();
        this.price = tinting.getPrice();
        this.stockQuantity = tinting.getStockQuantity();
        this.uvProtectionRate = tinting.getUvProtectionRate();
        this.irProtectionRate = tinting.getIrProtectionRate();
        this.visibleLightTransmittance = tinting.getVisibleLightTransmittance();
        this.color = tinting.getColor();

    }

}
