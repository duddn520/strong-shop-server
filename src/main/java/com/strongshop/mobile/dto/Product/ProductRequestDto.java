package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Item;
import com.strongshop.mobile.domain.Product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductRequestDto {

    private Long id;
    private String name;
    private String additionalInfo;
    private Item item;
    private Long companyId;

    public Product toEntity(){
        return Product.builder()
                .id(id)
                .name(name)
                .additionalInfo(additionalInfo)
                .item(item)
                .companyId(companyId)
                .build();
    }

}
