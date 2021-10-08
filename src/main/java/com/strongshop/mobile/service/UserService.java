package com.strongshop.mobile.service;

import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.User.UserRequestDto;
import com.strongshop.mobile.dto.User.UserResponseDto;
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
        User user = userRepository.findByNickname(userName)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 없습니다."));
        //log.info("토큰으로부터 사용자추출"+user.getId().toString());
        return user;
    }

    @Transactional
    public UserResponseDto registerUser(UserRequestDto requestDto)
    {
        User user = userRepository.findByEmail(requestDto.getEmail())           //email로 고객 조회, 최초가입자는 카카오에서 받아온 정보로 회원가입, 기존 가입자는
                .orElseGet(()-> new User());

        User saveuser = user.updateUser(requestDto);

        return new UserResponseDto(userRepository.save(saveuser));
    }

    @Transactional
    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }

}
