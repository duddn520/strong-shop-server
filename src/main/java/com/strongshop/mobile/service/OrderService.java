package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Car.CarRepository;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.Order.OrderRequestDto;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import com.strongshop.mobile.vo.TintingOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final TintingOptionRepository tintingOptionRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Transactional
    public Order saveOrder(OrderRequestDto requestDto, Authentication authentication) {
        //1. User를 셋하고
        User user = userService.getUserByToken(authentication.getPrincipal());
        requestDto.setUser(user);
        //2. CarId로 car찾기
        Car car = carRepository.findById(requestDto.getCarId())
                .orElseThrow(() -> new RuntimeException("No such car"));
        requestDto.setCar(car);

        return orderRepository.save(requestDto.toEntity());

    }

    @Transactional
    public Order saveOrderTest(OrderRequestDto requestDto) {
        //User user = userService.getUserByToken(authentication.getPrincipal());
        User user = new User(1L, "seungjin");
        Car car = new Car(1L, user, "12나1234");
        User user1 = userRepository.save(user);
        Car car1 = carRepository.save(car);

        var result = requestDto.getTintingOption();
//        tintingOptionRepository.save(result);
        requestDto.setUser(user1);
        requestDto.setCar(car1);

        // order가 save될때 tintingoption이랑 ppfoption도 같이 저장됨
        return orderRepository.save(requestDto.toEntity());

    }

    @Transactional
    public OrderResponseDto getOrderForUser(Authentication authentication) {
        User user = userService.getUserByToken(authentication.getPrincipal());
        Order order = orderRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("해당 유저의 주문요청이 없습니다."));
        return OrderResponseDto.builder()
                .tintingOption(order.getTintingOption())
                .build();
    }

    @Transactional
    public OrderResponseDto getOrderForUserTest() {
        User user = new User(1L, "seungjin");
        Order order = orderRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("해당 유저의 주문요청이 없습니다."));
        log.info(order.getUser().getRealName());
        log.info(order.getTintingOption().getPositionFront().toString());

        if (order.getTintingOption() != null) {
            return OrderResponseDto.builder()
                    .tintingOption(order.getTintingOption())
                    .user(user)
                    .build();
        }

        return null;
    }



}
