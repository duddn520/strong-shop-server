package com.strongshop.mobile.domain.User;

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
public class User implements UserDetails {

    @Id
    // 카카오에서 받은 고유ID로 PK설정할것이므로 자동전략 사용하지 않는다.
    private Long id;

    private String realName;    //사용자
    private String email;   //카카오
    private String username;  //사용자
    private String phoneNumber; //사용자
    private String profileImage; // 카카오
    private String thumbnailImage;  //카카오
    private String gender;  //카카오
    private String refreshToken; //카카오
    private LocalDate birth;

    public User(Long id, String realName) {
        this.id = id;
        this.realName = realName;
    }
    @Builder
    public User(Long id, String realName, String email, String username, String phoneNumber, String profileImage, String thumbnailImage, String gender, String refreshToken, LocalDate birth) {
        this.id = id;
        this.realName = realName;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
        this.gender = gender;
        this.refreshToken = refreshToken;
        this.birth = birth;

    }

    public User update(UserRequestDto requestDto) {
        this.realName = requestDto.getRealName();
        this.username = requestDto.getUserName();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.birth = requestDto.getBirth();

        return this;
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