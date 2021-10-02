package com.strongshop.mobile.dto.Product.Item;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Tinting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TintingRequestDto {

    private Long id;
    private String name;
    private Long companyId;
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
