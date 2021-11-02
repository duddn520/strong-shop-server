package com.strongshop.mobile.jwt;

import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.User.UserDto;
import com.strongshop.mobile.exception.UserEmailNotFoundException;
import com.strongshop.mobile.service.TokenService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = ((HttpServletRequest)request).getHeader("Auth");
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("UTF-8");

        if (token != null && jwtTokenProvider.verifyToken(token)) {     //토큰 유효성 검증 및 auth객체 생성 후 SecurityContextHolder에 등록.
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else if(token == null)
        {
            writer.println("Token required");
        }
        else if(!jwtTokenProvider.verifyToken(token))
        {
            writer.println("Token expoired");
        }

        chain.doFilter(request, response);
    }
}
