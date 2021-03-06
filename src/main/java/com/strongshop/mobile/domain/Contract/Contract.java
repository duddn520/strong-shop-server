package com.strongshop.mobile.domain.Contract;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.domain.Order.Kind;
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

    private Long companyId;
    private Long userId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "bidding_id")
    private Bidding bidding;

    @Column(length = 1024)
    private String detail;
    private String shipmentLocation;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Kind kind;

    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL)
    private List<ConstructionImageUrl> constructionImageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL)
    private List<InspectionImageUrl> inspectionImageUrls = new ArrayList<>();

    @Builder
    public Contract (Long id, Order order, Bidding bidding,String shipmentLocation, String detail, State state,Kind kind, List<ConstructionImageUrl> constructionImageUrls, List<InspectionImageUrl> inspectionImageUrls)
    {
        this.id = id;
        this.companyId = bidding.getCompany().getId();
        this.userId = order.getUser().getId();
        this.order = order;
        this.bidding = bidding;
        this.shipmentLocation = shipmentLocation;
        this.detail = detail;
        this.state = state;
        this.kind = kind;
        this.constructionImageUrls = constructionImageUrls;
        this.inspectionImageUrls = inspectionImageUrls;
    }

    public void updateState(State state)
    {
        this.state = state;
    }


}
