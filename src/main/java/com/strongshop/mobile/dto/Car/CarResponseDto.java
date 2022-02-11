package com.strongshop.mobile.dto.Car;

import com.strongshop.mobile.domain.Car.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarResponseDto {
    private Long id;
    private String carModel;
    private String carNo;

    public CarResponseDto(Car car)
    {
        this.id = car.getId();
        this.carModel = car.getCarModel();
        this.carNo = car.getCarNo();
    }
}
