package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Product.Product;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
@Getter
public class Tinting extends Product {
    private int uvProtectionRate;       //자외선
    private int irProtectionRate;       //적외선
    private int visibleLightTransmittance;          //가시광선투과율
    private String color;
}
