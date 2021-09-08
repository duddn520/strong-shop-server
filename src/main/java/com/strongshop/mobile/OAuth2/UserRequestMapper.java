package com.strongshop.mobile.OAuth2;

import com.strongshop.mobile.dto.User.UserRequestDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserRequestMapper {
    public UserRequestDto toRequestDto(OAuth2User oAuth2User){
        var attributes = oAuth2User.getAttributes();
        return UserRequestDto.builder()
                .id((Long) attributes.get("id"))
                .userName((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .phoneNumber((String) attributes.get("phone_number"))
                .profileImage((String) attributes.get("profile_image_url"))
                .thumbnailImage((String) attributes.get("thumbnail_image_url"))
                .gender((String) attributes.get("gender"))
                .birth((LocalDate) attributes.get("birthday"))
                .build();

    }
}
