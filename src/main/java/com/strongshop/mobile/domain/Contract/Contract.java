package com.strongshop.mobile.domain.Contract;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Contract {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "bidding_id")
    private Bidding bidding;

    private String detail;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "contract")
    private List<ConstructionImageUrl> constructionImageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "contract")
    private List<InspectionImageUrl> inspectionImageUrls = new ArrayList<>();

    @Builder
    public Contract (Long id, Order order, Bidding bidding, String detail, State state)
    {

    }

}
