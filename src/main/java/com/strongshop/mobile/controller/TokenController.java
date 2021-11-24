//Token refresh용 controller

//package com.strongshop.mobile.controller;
//
//import com.strongshop.mobile.domain.User.Role;
//import com.strongshop.mobile.jwt.JwtTokenProvider;
//import com.strongshop.mobile.model.ApiResponse;
//import com.strongshop.mobile.model.HttpResponseMsg;
//import com.strongshop.mobile.model.HttpStatusCode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@RequiredArgsConstructor
//@Controller
//public class TokenController {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @GetMapping("/token/refresh")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
//
//        String token = request.getHeader("Auth");
//        String fcmToken = request.getHeader("FCM");
//
//        Object role = jwtTokenProvider.getRole(token);
//        System.out.println("token = " + token);
//
//        System.out.println("role = " + role);
//
//        if(token!= null && jwtTokenProvider.verifyToken(token)){
//            String email = jwtTokenProvider.getEmail(token);
//            String newtoken = jwtTokenProvider.createToken(email, Role.valueOf((String) role));
//
//            response.addHeader("Auth",newtoken);
//            response.setContentType("application/json;charset=UTF-8");
//
//        }
//        else
//            throw new RuntimeException();
//
//    }
//}
