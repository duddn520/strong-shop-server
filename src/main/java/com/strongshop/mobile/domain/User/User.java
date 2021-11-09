package com.strongshop.mobile.domain.User;

import com.strongshop.mobile.dto.User.UserDto;
import com.strongshop.mobile.dto.User.UserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Getter
@NoArgsConstructor
@Entity
public class User implements UserDetails{

    @Id @GeneratedValue
    private Long id;
    private String realName;    //사용자
    private String email;   //카카오
    private String nickname;  //사용자
    private String phoneNumber; //사용자
    private String profileImage; // 카카오
    private String thumbnailImage;  //카카오
    private String gender;  //카카오
    private LocalDate birth;

    @Builder
    public User(Long id,String realName, String email, String nickname, String phoneNumber, String profileImage, String thumbnailImage, String gender, LocalDate birth) {
        this.id = id;
        this.realName = realName;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
        this.gender = gender;
        this.birth = birth;

    }

    public User updateUser(UserRequestDto requestDto) {
        this.id = requestDto.getId();
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
        this.profileImage = requestDto.getProfileImage();
        this.thumbnailImage = requestDto.getThumbnailImage();
        return this;
    }

    public UserDto toUserDto(){
        return UserDto.builder()
                .id(id)
                .realName(realName)
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .phoneNumber(phoneNumber)
                .thumbnailImage(thumbnailImage)
                .gender(gender)
                .birth(birth)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}