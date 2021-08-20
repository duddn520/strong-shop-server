package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Product.Product;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
@Getter
public class Ppf extends Product {
}
