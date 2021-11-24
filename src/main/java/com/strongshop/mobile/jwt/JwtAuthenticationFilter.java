package com.strongshop.mobile.jwt;

import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = ((HttpServletRequest)request).getHeader("Auth");
        response.setCharacterEncoding("UTF-8");

        if (token != null && jwtTokenProvider.verifyToken(token)) {     //토큰 유효성 검증 및 auth객체 생성 후 SecurityContextHolder에 등록.
            Role role =  Role.valueOf((String)jwtTokenProvider.getRole(token));
            Authentication auth = jwtTokenProvider.getAuthentication(token,role);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }


        chain.doFilter(request, response);
    }
}
