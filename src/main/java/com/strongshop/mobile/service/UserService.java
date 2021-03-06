package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Car.CarRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Transactional
    // 토큰으로부터 유저엔티티 조회
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException());
        return user;
    }

    @Transactional
    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }

    @Transactional
    public void removeFcmToken(User user) {
        user.removeFcmToken();

        userRepository.save(user);
    }

    @Transactional
    public void updateCar(User user,Car car){
        user.getCars().add(car);
        userRepository.save(user);
    }

    @Transactional
    public List<Car> getCarList(User user)
    {
        List<Car> cars = carRepository.findAllByUserId(user.getId())
                .orElseGet(()->new ArrayList<>());

        return cars;
    }

    @Transactional
    public User getUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException());
        return user;
    }
}
