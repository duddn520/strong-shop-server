package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class TokenController {
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){

        String token = request.getHeader("Auth");
        Object role = jwtTokenProvider.getRole(token);
        System.out.println("token = " + token);

        System.out.println("role = " + role);

        if(token!= null && jwtTokenProvider.verifyToken(token)){
            String email = jwtTokenProvider.getEmail(token);
            String newtoken = jwtTokenProvider.createToken(email, Role.valueOf((String) role));

            response.addHeader("Auth",newtoken);
            response.setContentType("application/json;charset=UTF-8");

        }
        else
            throw new RuntimeException();

    }
}
