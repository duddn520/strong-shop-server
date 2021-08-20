package com.strongshop.mobile.domain.Review;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Review {

    @Id @GeneratedValue
    private Long id;


}
