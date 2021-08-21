package com.strongshop.mobile.domain.Gallary;

import com.strongshop.mobile.domain.Company.Company;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Gallary {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
}
