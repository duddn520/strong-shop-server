package com.strongshop.mobile.dto.Car;

import com.strongshop.mobile.domain.Car.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CarRequestDto {
    private Long id;
    private String carModel;
    private String carNo;

    public Car toEntity(){
        return Car.builder()
                .carModel(carModel)
                .carNo(carNo)
                .build();
    }
}
