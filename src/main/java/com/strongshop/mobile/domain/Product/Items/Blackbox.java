package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Product.Product;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
public class Blackbox extends Product {

    private String resolution; //해상도
    private int duration;   //지속시간..
}
