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

    @Column(length = 1024)
    private String detail;
    private String shipmentLocation;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL)
    private List<ConstructionImageUrl> constructionImageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL)
    private List<InspectionImageUrl> inspectionImageUrls = new ArrayList<>();

    @Builder
    public Contract (Long id, Order order, Bidding bidding,String shipmentLocation, String detail, State state, List<ConstructionImageUrl> constructionImageUrls, List<InspectionImageUrl> inspectionImageUrls)
    {
        this.id = id;
        this.order = order;
        this.bidding = bidding;
        this.shipmentLocation = shipmentLocation;
        this.detail = detail;
        this.state = state;
        this.constructionImageUrls = constructionImageUrls;
        this.inspectionImageUrls = inspectionImageUrls;
    }

    public void updateState(State state)
    {
        this.state = state;
    }

    public void updateInspectionImageUrls(List<InspectionImageUrl> imageUrls)
    {
        this.inspectionImageUrls = imageUrls;
    }

    public void updateConstructionImageUrls(List<ConstructionImageUrl> imageUrls)
    {
        this.constructionImageUrls = imageUrls;
    }

}
