package com.strongshop.mobile.OAuth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strongshop.mobile.dto.User.UserRequestDto;
import com.strongshop.mobile.dto.User.UserResponseDto;
import com.strongshop.mobile.service.TokenService;
import com.strongshop.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserService userService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;

    //카카오 토큰으로 인증이 완료되었을때 실행되는 핸들러, 자동적인 JWT 발급을 위해 사용.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserRequestDto requestDto = userRequestMapper.toRequestDto(oAuth2User);

        Token token = tokenService.generateToken(requestDto.getEmail(),"USER");         //jwt발급

        requestDto.updateRefreshToken(token);               //requestDto에 리프레쉬토큰 정보 담기
        //최초로그인 -> 회원가입. 기존회원 -> 업데이트.
        userService.registerUser(requestDto);

        //response에 토큰 담겨서 반환.
        writeTokenResponse(response,token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException{
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth",token.getToken());
        response.addHeader("Refresh",token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
