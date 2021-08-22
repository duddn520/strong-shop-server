package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@NoArgsConstructor
@Getter
public class Blackbox extends Product {

    private String resolution; //해상도
    private String duration;   //지속시간..

    @Builder
    public Blackbox(Long id, String name, Company company, String manufacturer, int price, int stockQuantity, String duration, String resolution)
    {
        super(id,name,company,manufacturer,price,stockQuantity);
        this.duration = duration;
        this.resolution = resolution;
    }
}
