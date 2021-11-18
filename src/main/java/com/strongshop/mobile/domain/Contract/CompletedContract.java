package com.strongshop.mobile.domain.Contract;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CompletedContract extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Long companyId;
    private String companyName;
    private String companyThumbnailImage;

    @Column(length = 1024)
    private String details;             //bidding
    private String shipmentLocation;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    @Builder
    public CompletedContract(Long id,User user, Long companyId, String companyName,String companyThumbnailImage, String details, String shipmentLocation, ReviewStatus reviewStatus)
    {
        this.id = id;
        this.user = user;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyThumbnailImage = companyThumbnailImage;
        this.details = details;
        this.shipmentLocation = shipmentLocation;
        this.reviewStatus = reviewStatus;
    }


}
