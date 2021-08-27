package com.strongshop.mobile.dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// 회원가입 요청시
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {

    private Long id;
    private String realName;
    private String userName;
    private String phoneNumber;
    private LocalDate birth;

    @Builder
    public UserRequestDto(Long id, String realName, String userName, String phoneNumber, LocalDate birth) {
        this.id = id;
        this.realName = realName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }
}
