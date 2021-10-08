package com.strongshop.mobile.controller;

import com.strongshop.mobile.dto.Car.CarRequestDto;
import com.strongshop.mobile.dto.Car.CarResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/api/car")
    public ResponseEntity<ApiResponse<CarResponseDto>> registerCar(@RequestBody CarRequestDto requestDto)
    {
        CarResponseDto responseDto = carService.registerCar(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }
}
