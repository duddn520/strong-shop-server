package com.strongshop.mobile.domain.User;

import com.strongshop.mobile.domain.Car.Car;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;

    @OneToMany(mappedBy = "user")
    private List<Car> cars = new ArrayList<>();

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
