package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Car.CarRepository;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.Order.OrderRequestDto;
import com.strongshop.mobile.vo.TintingOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

//    private final UserService userService;
    private final OrderRepository orderRepository;
    private final TintingOptionRepository tintingOptionRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

//    @Transactional
//    public Order saveOrder(OrderRequestDto requestDto, Authentication authentication) {
//        User user = userService.getUserByToken(authentication.getPrincipal());
//
//        requestDto.setUser(user);
//        return orderRepository.save(requestDto.toEntity());
//
//    }

    @Transactional
    public Order saveOrderTest(OrderRequestDto requestDto) {
        //User user = userService.getUserByToken(authentication.getPrincipal());
        User user = new User(1L, "seungjin");
        Car car = new Car(1L, user, "12나1234");
        User user1 = userRepository.save(user);
        Car car1 = carRepository.save(car);

        var result = requestDto.getTintingOption();
        System.out.println(result.getPositionFront());
//        tintingOptionRepository.save(result);
        requestDto.setUser(user1);
        requestDto.setCar(car1);

        return orderRepository.save(requestDto.toEntity());

    }


}
