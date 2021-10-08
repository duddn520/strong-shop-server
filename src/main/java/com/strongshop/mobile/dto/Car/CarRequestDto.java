package com.strongshop.mobile.dto.Car;


import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarRequestDto {

    private Long id;
    private User user;
    private Long userId;
    private String carNo;

    public Car toEntity(){
        return Car.builder()
                .id(id)
                .user(user)
                .carNo(carNo)
                .build();
    }
}
