package com.strongshop.mobile.controller;

import com.strongshop.mobile.OAuth2.Token;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

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
            String email = tokenService.getUserEmail(token);
            Token newToken = tokenService.generateToken(email,"USER");

            response.addHeader("Auth",newToken.getToken());
            response.addHeader("Refresh",newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

        }
        throw new RuntimeException();

    }

    @GetMapping("/api/login")
    public ResponseEntity<ApiResponse<String>> loginWithAccessToken(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        System.out.println("accessToken = " + accessToken);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        headers.add("Content-type","application/json;charset=utf-8");
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(params,headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> responseEntity = rt.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                String.class);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseEntity.getBody()),HttpStatus.OK);

    }
}