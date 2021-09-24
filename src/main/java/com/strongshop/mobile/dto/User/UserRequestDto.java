package com.strongshop.mobile.dto.User;

import com.strongshop.mobile.OAuth2.Token;
import com.strongshop.mobile.domain.User.Role;
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
    private String refreshToken; //카카오
    private LocalDate birth;

    @Builder
    public UserRequestDto(Long id, Role role,String realname,String email, String nickname, String phoneNumber, String profileImage, String thumbnailImage, String gender
                          , String refreshToken, LocalDate birth) {
        this.id = id;
        this.realname = realname;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
        this.gender = gender;
        this.refreshToken = refreshToken;
        this.birth = birth;
    }

    public User toEntity(){
        return User.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .profileImage(profileImage)
                .thumbnailImage(thumbnailImage)
                .refreshToken(refreshToken)
                .build();
    }

    public void updateRefreshToken(Token token)
    {
        this.refreshToken = token.getRefreshToken();
    }
}
