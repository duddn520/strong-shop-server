package com.strongshop.mobile.domain.Product;

import com.strongshop.mobile.domain.Company.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Company company;

    private String manufacturer;

    private int price;
    private int stockQuantity;

    public void updateCompany(Company company){
        this.company = company;
    }

}