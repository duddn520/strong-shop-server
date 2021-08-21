package com.strongshop.mobile.domain.Review;

import com.strongshop.mobile.domain.Company.Company;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Review {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


}
