package com.strongshop.mobile.domain.Contract;

import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Company.Company;
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

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(length = 1024)
    private String details;             //bidding
    private String shipmentLocation;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    @Builder
    public CompletedContract(Long id,User user, Company company, String details, String shipmentLocation, ReviewStatus reviewStatus)
    {
        this.id = id;
        this.user = user;
        this.company = company;
        this.details = details;
        this.shipmentLocation = shipmentLocation;
        this.reviewStatus = reviewStatus;
    }

    public void updateReviewStatus()
    {
        this.reviewStatus = ReviewStatus.WRITTEN;
    }

}
