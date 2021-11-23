package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Product.Item;
import com.strongshop.mobile.domain.Product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String additionalInfo;
    private Item item;
    private Long companyId;

    public ProductResponseDto(Product product)
    {
        this.id = product.getId();
        this.name = product.getName();
        this.additionalInfo = product.getAdditionalInfo();
        this.item = product.getItem();
        this.companyId = product.getId();
    }
}
