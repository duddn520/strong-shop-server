package com.strongshop.mobile.controller;

import com.strongshop.mobile.OAuth2.Token;
import com.strongshop.mobile.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/token/refresh")
    public void refreshAuth(HttpServletRequest request, HttpServletResponse response){

        String token = request.getHeader("Refresh");

        if(token!= null && tokenService.verifyToken(token)){
            String email = tokenService.getUid(token);
            Token newToken = tokenService.generateToken(email,"USER");

            response.addHeader("Auth",newToken.getToken());
            response.addHeader("Refresh",newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

        }
        throw new RuntimeException();

    }
}
