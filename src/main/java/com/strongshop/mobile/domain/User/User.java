package com.strongshop.mobile.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    private String picture;


    @Builder
    public User(String userName, String email, String picture) {
        this.userName = userName;
        this.email = email;
        this.picture = picture;

    }

    public User update(String userName, String picture){
        this.userName = userName;
        this.picture = picture;

        return this;
    }

}
