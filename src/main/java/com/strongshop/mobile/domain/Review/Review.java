package com.strongshop.mobile.domain.Review;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.strongshop.mobile.domain.BaseEntity;
import com.strongshop.mobile.domain.Company.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Review extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    private String content;
    private float rating;

    @Builder
    public Review(Company company, String content, float rating)
    {
        this.company = company;
        this.content = content;
        this.rating = rating;
    }

    public void updateCompany(Company company)
    {
        this.company = company;
    }



}
