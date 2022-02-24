package com.strongshop.mobile.jwt;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.JwtCompanyUserDetailService;
import com.strongshop.mobile.service.JwtUserUserDetailService;
import com.strongshop.mobile.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;

/*
토큰을 생성하고 검증하는 컴포넌트
실제로 이 컴포넌트를 이용하는것은 인증작업을 진행하는 Filter클래스
Filter는 검증이 끝난 JWT로부터 유저정보를 받아와서 UsernamePasswordAuthenticationFilter로 전달 */
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    // Security UserDetailService
    private final JwtUserUserDetailService jwtUserUserDetailService;
    private final JwtCompanyUserDetailService jwtCompanyUserDetailService;
    private final CompanyService companyService;
    private final UserService userService;

    // 보호키
    private String secretKey = "STRONG_DEALDER";

    //토큰 지속시간 60분
    private static final long TokenValidTime = 60 * 60 * 1000L * 24 * 7 * 2; //jwt 유효기간 2주로
//    private static final long TokenValidTime = 60 * 60 * 1000L * 24 * 7 * 2; //jwt 유효기간 2주로

    //객체초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT토큰생성
    public String createToken(String email, Role roles,String fcmToken) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("fcmtoken",fcmToken);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }


    //JWT토큰에서 인증정보 조회
    public Authentication getAuthentication(String token, Role role){
        // 토큰에서 추출한 회원정보를 받아 유저를 로드하고 이를 userDetails에 담아 UsernamePasswordAuthenticationToken에 담아보낸다.x
        if(role==Role.USER) {
            UserDetails userDetails = jwtUserUserDetailService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }
        else if(role == Role.COMPANY)
        {
            UserDetails userDetails = jwtCompanyUserDetailService.loadUserByUsername(this.getEmail(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }
        else if(role ==Role.DEALER)
        {
          UserDetails userDetails = jwtUserUserDetailService.loadUserByUsername(this.getEmail(token));
          return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
        }
        else
            throw new RuntimeException("유저 정보 조회 실패");
    }

    //토큰에서 회원정보(username) 추출
    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Object getRole(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("roles");
    }

    public String getFcmToken(String token){
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("fcmtoken");
    }

    // HTTP요청헤더에서 token추출
    public String getToken(HttpServletRequest request) {
        return request.getHeader("Auth");
    }

    // 토큰 만료일자 확인
    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            boolean exp = !claims.getBody().getExpiration().before(new Date());
            boolean fcm = true;

            Role role = Role.valueOf((String) getRole(token));
            if(Role.USER.equals(role)) {
                User user = userService.getUserByEmail(claims.getBody().getSubject());
                if (user.getFcmToken().equals(claims.getBody().get("fcmtoken")))             //유저 fcm과 비교.
                    fcm = true;
                else
                    fcm = false;
            }else
            {
                Company company = companyService.getCompanyByEmail(claims.getBody().getSubject());
                if(company.getFcmToken().equals(claims.getBody().get("fcmtoken")))
                    fcm=true;
                else
                    fcm=false;                                          //company fcm과 비교.
            }

            return fcm&&exp;

        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public String getFcmTokenByJwtToken (String token)
    {
        Role role = Role.valueOf((String) getRole(token));
        String email = getEmail(token);
        if (role.equals(Role.COMPANY))
        {
            Company company = companyService.getCompanyByEmail(email);
            return company.getFcmToken();
        }
        else if(role.equals(Role.USER))
        {
            User user = userService.getUserByEmail(email);
            return user.getFcmToken();
        }
        else
            return null;
    }
}
