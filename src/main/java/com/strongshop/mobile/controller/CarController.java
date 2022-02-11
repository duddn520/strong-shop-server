package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Bidding.BiddingHistory;
import com.strongshop.mobile.domain.Car.Car;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.Bidding.BiddingHistoryResponseDto;
import com.strongshop.mobile.dto.Car.CarRequestDto;
import com.strongshop.mobile.dto.Car.CarResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @PostMapping("/api/car")
    @Transactional
    public ResponseEntity<ApiResponse<CarResponseDto>> getBiddingHistories(@RequestBody CarRequestDto requestDto, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        Car car = Car.builder()
                .carModel(requestDto.getCarModel())
                .carNo(requestDto.getCarNo())
                .build();

        userService.updateCar(user,car);

        CarResponseDto responseDto = new CarResponseDto(car);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDto), HttpStatus.OK);

    }

}
