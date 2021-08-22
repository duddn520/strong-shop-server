package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
@NoArgsConstructor
@Getter
public class Tinting extends Product {
    private int uvProtectionRate;       //자외선
    private int irProtectionRate;       //적외선
    private int visibleLightTransmittance;          //가시광선투과율
    private String color;

    @Builder
    public Tinting(Long id, String name, Company company, String manufacturer, int price, int stockQuantity, String duration, String resolution,
                    int uvProtectionRate, int irProtectionRate, int visibleLightTransmittance, String color) {

        super(id, name, company, manufacturer, price, stockQuantity);
        this.uvProtectionRate = uvProtectionRate;
        this.irProtectionRate = irProtectionRate;
        this.visibleLightTransmittance = visibleLightTransmittance;
        this.color = color;
    }
}
