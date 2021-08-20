package com.strongshop.mobile.domain.Product;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Product {

    @Id @GeneratedValue
    private Long id;

    private String manufacturer;

    private int price;
    private int stockQuantity;

}
