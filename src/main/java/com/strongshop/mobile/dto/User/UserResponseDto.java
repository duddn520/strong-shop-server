package com.strongshop.mobile.dto.User;

import com.strongshop.mobile.domain.User.LoginMethod;
import com.strongshop.mobile.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String realName;    //사용자
    private String email;   //카카오
    private String nickname;  //사용자
    private String phoneNumber; //사용자
    private String profileImage; // 카카오
    private String thumbnailImage;  //카카오
    private String gender;  //카카오
    private LocalDate birth;
    private LoginMethod loginMethod;



    public UserResponseDto(User user){
        this.id = user.getId();
        this.realName = user.getRealName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImage = user.getProfileImage();
        this.thumbnailImage = user.getThumbnailImage();
        this.gender = user.getGender();
        this.birth = user.getBirth();
        this.loginMethod = user.getLoginMethod();
    }
}
