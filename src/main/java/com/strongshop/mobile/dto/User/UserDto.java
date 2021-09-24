package com.strongshop.mobile.dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UserDto {

    private Long id;
    private String realName;    //사용자
    private String email;   //카카오
    private String nickname;  //사용자
    private String phoneNumber; //사용자
    private String profileImage; // 카카오
    private String thumbnailImage;  //카카오
    private String gender;  //카카오
    private String refreshToken; //카카오
    private LocalDate birth;

    @Builder
    public UserDto(Long id, String realName, String email, String nickname, String phoneNumber, String profileImage, String thumbnailImage, String gender, String refreshToken, LocalDate birth)
    {
        this.id = id;
        this.realName = realName;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
        this.gender = gender;
        this.refreshToken = refreshToken;
        this.birth = birth;
    }
}
