package com.strongshop.mobile.domain.Car;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
public class Car {

    @Id
    private Long id;

    //유저 매핑필요

}
