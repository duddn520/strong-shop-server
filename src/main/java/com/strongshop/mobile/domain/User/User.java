package com.strongshop.mobile.domain.User;

import com.strongshop.mobile.domain.Car.Car;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;

    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
