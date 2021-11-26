package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.User.UserRequestDto;
import com.strongshop.mobile.dto.User.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    // 토큰으로부터 유저엔티티 조회
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException());
        return user;
    }

    @Transactional
    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }

    @Transactional
    public void removeFcmToken(User user) {
        user.removeFcmToken();

        userRepository.save(user);
    }
}
