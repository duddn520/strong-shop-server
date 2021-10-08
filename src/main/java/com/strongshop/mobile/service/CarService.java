package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Car.CarRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.Car.CarRequestDto;
import com.strongshop.mobile.dto.Car.CarResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Transactional
    public CarResponseDto registerCar(CarRequestDto requestDto)
    {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(()-> new RuntimeException());
        requestDto.setUser(user);
        Car car = requestDto.toEntity();
        return new CarResponseDto(carRepository.save(car));
    }

}
