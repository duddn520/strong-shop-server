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
    private String filename;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Builder
    public ConstructionImageUrl(Long id, String imageUrl,String filename,Contract contract)
    {
        this.id = id;
        this.imageUrl = imageUrl;
        this.filename = filename;
        this.contract = contract;

    }

    public void updateContract(Contract contract)
    {
        this.contract = contract;
        contract.getConstructionImageUrls().add(this);
    }


}
