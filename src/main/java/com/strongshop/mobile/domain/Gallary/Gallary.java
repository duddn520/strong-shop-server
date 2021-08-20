package com.strongshop.mobile.domain.Gallary;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Gallary {

    @Id
    @GeneratedValue
    private Long id;
}
