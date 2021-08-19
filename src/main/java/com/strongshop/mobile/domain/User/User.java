package com.strongshop.mobile.domain.User;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Order.Order;
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
    private List<Order> orders = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Car> cars = new ArrayList<>();

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
