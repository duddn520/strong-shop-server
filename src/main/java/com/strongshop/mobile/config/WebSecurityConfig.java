package com.strongshop.mobile.config;

import com.strongshop.mobile.OAuth2.OAuth2SuccessHandler;
import com.strongshop.mobile.OAuth2.StrongShopOAuth2UserService;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.jwt.JwtAuthenticationFilter;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()

                .and()
                .httpBasic().disable()

                .csrf().ignoringAntMatchers("/h2-console/**").disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰기반 인증이므로 세션을 사용하지 않는다.

                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/token/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/uauth/**").authenticated()
                .anyRequest().permitAll();


        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),UsernamePasswordAuthenticationFilter.class);

    }
}
