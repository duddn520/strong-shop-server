package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    // 토큰으로부터 유저엔티티 조회
    public User getUserByToken(Object principal) {
        String userName = ((UserDetails) principal).getUsername();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 없습니다."));
        //log.info("토큰으로부터 사용자추출"+user.getId().toString());
        return user;
    }
}
