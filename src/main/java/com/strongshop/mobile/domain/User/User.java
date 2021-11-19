package com.strongshop.mobile.domain.User;

import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.dto.User.UserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private String fcmToken;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<CompletedContract> completedContracts = new ArrayList<>();

    @Builder
    public User(Long id,String realName, String email, String nickname, String phoneNumber, String profileImage, String thumbnailImage, String gender, LocalDate birth, List<Order> orders, String fcmToken, List<CompletedContract> completedContracts) {
        this.id = id;
        this.realName = realName;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
        this.gender = gender;
        this.birth = birth;
        this.orders =orders;
        this.fcmToken = fcmToken;
        this.completedContracts = completedContracts;


    }

    public User updateUser(UserRequestDto requestDto) {
        this.id = requestDto.getId();
        this.nickname = requestDto.getNickname();
        this.email = requestDto.getEmail();
        this.profileImage = requestDto.getProfileImage();
        this.thumbnailImage = requestDto.getThumbnailImage();
        return this;
    }

    public void updateFcmToken(String token)
    {
        this.fcmToken = token;
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