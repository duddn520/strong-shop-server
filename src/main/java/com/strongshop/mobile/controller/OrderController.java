package com.strongshop.mobile.controller;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.State;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.Order.OrderRequestDto;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.OrderService;
import com.strongshop.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
import org.apache.commons.io.IOUtils;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/api/orders",produces = "application/json; charset=utf8")
    @Transactional
    public ResponseEntity<ApiResponse<OrderResponseDto>> registerOrder(@RequestBody Map<String,Object> param,HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        String details = (String) param.get("details");
        String region = (String) param.get("region");
        Order order = Order.builder()
                .detail(details)
                .region(region)
                .state(State.BIDDING)
                .build();
        order.updateOrder(user);

        OrderResponseDto responseDto = orderService.saveOrder(order);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getOrdersNowBidding(@RequestParam List<String> regions)
    {
        List<OrderResponseDto> responseDtos = new ArrayList<>();

        List<Order> orders = orderService.getOrdersStateIsBidding();
        for(Order o : orders)
        {
            if (o.getCreatedTime().plusMinutes(1).isBefore(LocalDateTime.now()))
            {
                orderService.updateState2BiddingComplete(o);
            }
            else {

                for (String region : regions) {

                    if (o.getRegion() == region) {
                        OrderResponseDto responseDto = new OrderResponseDto(o);
                        responseDtos.add(responseDto);
                    }
                }
            }
        }
        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @GetMapping("/api/orders/user")     //유저가 조회
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getMyOrders(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        List<Order> orders = orderService.getOrdersByUser(user);

        List<OrderResponseDto> responseDtos = new ArrayList<>();

        for (Order o : orders)
        {
            if(o.getState()== State.BIDDING)
            {
                if (o.getCreatedTime().plusMinutes(1).isBefore(LocalDateTime.now()))
                {
                    orderService.updateState2BiddingComplete(o);
                }
            }
            OrderResponseDto responseDto = new OrderResponseDto(o);
            responseDtos.add(responseDto);
        }
        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }


}