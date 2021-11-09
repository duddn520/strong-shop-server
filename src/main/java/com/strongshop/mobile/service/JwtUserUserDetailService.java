package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/*
UserDetailsService를 상속받아 재정의하는 클래스 -> 토큰에 저장된 유저정보를 활용하기위함
DB에서 UserDetail을 얻어와 AuthenticationManager에게 제공하는 역할을 수행합니다.
 */
@RequiredArgsConstructor
@Service
public class JwtUserUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    // 상세정보를 조회하는 메소드 , 사용자의 계정정보와 권한을 갖는 UserDetails인터페이스를 반환한다.
    // 매개변수는 로그인시 입력한 이메일 (이메일로 회원정보 조회)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB에서 사용자정보를 가져와서 반환
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다."));

        return user;

    }
}
