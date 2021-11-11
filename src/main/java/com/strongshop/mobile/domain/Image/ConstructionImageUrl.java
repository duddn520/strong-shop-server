package com.strongshop.mobile.domain.Image;


import com.strongshop.mobile.domain.Contract.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ConstructionImageUrl {

    @Id @GeneratedValue
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Builder
    public ConstructionImageUrl(Long id, String imageUrl, Contract contract)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.contract = contract;
    }


}
