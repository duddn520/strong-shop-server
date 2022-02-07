package com.strongshop.mobile.controller;


import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Kind;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Order.OrderImage;
import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.Order.OrderResponseDto;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.FileUploadService;
import com.strongshop.mobile.service.OrderService;
import com.strongshop.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyService companyService;
    private final FileUploadService fileUploadService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping(value = "/api/orders/ncp",produces = "application/json; charset=utf8")
    @Transactional
    public ResponseEntity<ApiResponse<OrderResponseDto>> registerNCPOrder(@RequestBody Map<String,Object> param,HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        String details = (String) param.get("details");
        String region = (String) param.get("region");
        Order order = Order.builder()
                .detail(details)
                .region(region)
                .state(State.BIDDING)
                .kind(Kind.NewCarPackage)
                .build();
        order.updateOrder(user);

        OrderResponseDto responseDto = orderService.saveOrder(order);
        responseDto.setBidcount(0);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/api/orders/care",produces = "application/json; charset=utf8")
    @Transactional
    public ResponseEntity<ApiResponse<OrderResponseDto>> registerCareOrder(@RequestParam List<MultipartFile> imagefiles, @RequestParam List<String> comments, @RequestBody Map<String,Object> param , HttpServletRequest request)
    {

        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        List<OrderImage> orderImages = new ArrayList<>();

        for(int i = 0;i<imagefiles.size();i++){

            String filename = fileUploadService.uploadImage(imagefiles.get(i));
            String url = fileUploadService.getFileUrl(filename);
            OrderImage orderImage = OrderImage.builder()
                    .imageUrl(url)
                    .filename(filename)
                    .comment(comments.get(i))
                    .build();

            orderImages.add(orderImage);
        }

        String details = (String) param.get("details");
        String region = (String) param.get("region");
        Order order = Order.builder()
                .detail(details)
                .region(region)
                .state(State.BIDDING)
                .orderImages(orderImages)
                .kind(Kind.Care)
                .build();

        order.updateOrder(user);

        OrderResponseDto responseDto = orderService.saveOrder(order);
        responseDto.setBidcount(0);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/orders")          //업체용. TODO 내가 이미 입찰했으면 목록에서 제외하도록 해야함.
    @Transactional
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getOrdersNowBidding(@RequestParam List<String> regions, HttpServletRequest request) {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        List<OrderResponseDto> responseDtos = new ArrayList<>();
        List<Bidding> biddings = company.getBiddings();

        for (String region : regions) {
            List<Order> orders = orderService.getOrdersStateIsBiddingAndSearchedByRegion(region);

            for (Order order : orders) {
                if (order.getCreatedTime().plusDays(1).isBefore(LocalDateTime.now()))
                {
                    orderService.updateState2BiddingComplete(order);
                    try {
                        firebaseCloudMessageService.sendMessageTo(order.getUser().getFcmToken(), "입찰 요청 만료", "입찰 요청 만료", "001");
                    }catch (IOException e)
                    {
                        log.error("userId: {} failed to send fcm message. (OrderController.getOrderNowBidding)",order.getUser().getId());
                    }
                }
                else if(order.getState().equals(State.BIDDING)){
                    boolean flag = true;
                    for(Bidding b : biddings)
                    {
                        if(order.getBiddings().contains(b))             //company의 비딩중 해당 오더가 존재하면, flag -> false
                        {
                            flag=false;
                        }
                    }
                    if(flag)
                    {
                        OrderResponseDto responseDto = new OrderResponseDto(order);
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
    @Transactional
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getMyOrders4User(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        List<Order> orders = user.getOrders();

        List<OrderResponseDto> responseDtos = new ArrayList<>();

        for (Order o : orders)
        {
            if(o.getState()== State.BIDDING)
            {
                if (o.getCreatedTime().plusDays(1).isBefore(LocalDateTime.now()))
                {
                    orderService.updateState2BiddingComplete(o);
                    try {
                        firebaseCloudMessageService.sendMessageTo(o.getUser().getFcmToken(), "입찰 요청 만료", "입찰 요청 만료", "001");
                    }catch (IOException e)
                    {
                        log.error("userId: {} failed to send fcm message. (OrderController.getMyOrders4User)",user.getId());
                    }
                }
            }
            OrderResponseDto responseDto = new OrderResponseDto(o);
            List<Bidding> biddings = o.getBiddings();
            if(biddings!=null) {
                responseDto.setBidcount(biddings.size());
            }else
            {
                responseDto.setBidcount(0);
            }
            responseDtos.add(responseDto);
        }
        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/api/orders/{order_id}")
    @Transactional
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable("order_id") Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);

        order.getUser().getOrders().remove(order);
        orderService.deleteOrder(order);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/api/orders/{order_id}")
    public ResponseEntity<ApiResponse<Map<String,Object>>> getOrderInfo(@PathVariable("order_id") Long orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        Map<String,Object> map = new HashMap<>();

        map.put("detail",order.getDetail());
        map.put("state",order.getState());

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                map), HttpStatus.OK);
    }
}