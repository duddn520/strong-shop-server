package com.strongshop.mobile.dto.User;

import com.strongshop.mobile.domain.User.LoginMethod;
import com.strongshop.mobile.domain.User.User;
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
    private String realname;    //사용자
    private String email;   //카카오
    private String nickname;  //카카오
    private String phoneNumber; //사용자
    private String profileImage; // 카카오
    private String thumbnailImage;  //카카오
    private String gender;  //카카오
    private String businessNumber;
    private LocalDate birth;
    private String fcmToken;
    private LoginMethod loginMethod;
    private String role;

    @Builder
    public UserRequestDto(Long id,String realname,String email, String nickname, String phoneNumber, String profileImage, String thumbnailImage, String gender
                          ,String businessNumber, LocalDate birth, String fcmToken, LoginMethod loginMethod, String role) {
        this.id = id;
        this.realname = realname;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
        this.gender = gender;
        this.businessNumber = businessNumber;
        this.birth = birth;
        this.fcmToken = fcmToken;
        this.loginMethod = loginMethod;
        this.role = role;
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .realName(realname)
                .email(email)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .profileImage(profileImage)
                .thumbnailImage(thumbnailImage)
                .gender(gender)
                .businessNumber(businessNumber)
                .birth(birth)
                .fcmToken(fcmToken)
                .loginMethod(loginMethod)
                .build();
    }

}
