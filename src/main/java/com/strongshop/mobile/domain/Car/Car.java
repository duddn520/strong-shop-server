package com.strongshop.mobile.domain.Car;

import com.strongshop.mobile.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Car {

    @Id @GeneratedValue
    private Long id;

    private String carModel;
    private String carNo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Car (Long id, String carModel, String carNo)
    {
        this.id = id;
        this.carModel = carModel;
        this.carNo = carNo;
    }
}
