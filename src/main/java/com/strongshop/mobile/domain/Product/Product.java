package com.strongshop.mobile.domain.Product;

import com.strongshop.mobile.domain.Company.Company;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String additionalInfo;

    @Enumerated(EnumType.STRING)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public Product(Long id, String name, String additionalInfo, Item item, Company company){
        this.id = id;
        this.name = name;
        this.additionalInfo = additionalInfo;
        this.item = item;
        this.company = company;
    }

    public Product updateProduct(Product product)
    {
        this.name = product.getName();
        this.additionalInfo = product.getAdditionalInfo();
        this.item = product.getItem();
        this.company = product.getCompany();

        return this;
    }




}
