package com.strongshop.mobile.dto.Product;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Items.Blackbox;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlackboxResponseDto {

    private Long id;
    private String name;
    private Long company_id;
    private String manufacturer;
    private int price;
    private int stockQuantity;
    //Blackbox 속성
    private String duration;
    private String resolution;

    public BlackboxResponseDto(Blackbox blackbox)
    {
        this.id = blackbox.getId();
        this.name = blackbox.getName();
        this.company_id = blackbox.getCompany().getId();
        this.manufacturer = blackbox.getManufacturer();
        this.price = blackbox.getPrice();
        this.stockQuantity = blackbox.getStockQuantity();
        this.duration = blackbox.getDuration();
        this.resolution = blackbox.getResolution();
    }

}
