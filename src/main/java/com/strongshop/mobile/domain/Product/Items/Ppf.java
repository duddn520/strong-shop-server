package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
@NoArgsConstructor
@Getter
public class Ppf extends Product {

    @Builder
    public Ppf(Long id, String name, Company company, String manufacturer, int price, int stockQuantity, String duration, String resolution) {
        super(id, name, company, manufacturer, price, stockQuantity);
    }
}
