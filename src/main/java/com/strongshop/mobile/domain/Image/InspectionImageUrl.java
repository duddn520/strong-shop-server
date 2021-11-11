package com.strongshop.mobile.domain.Image;

import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Gallery.Gallery;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class InspectionImageUrl {

    @Id @GeneratedValue
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Builder
    public InspectionImageUrl(Long id, String imageUrl, Contract contract)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.contract = contract;
    }
}
