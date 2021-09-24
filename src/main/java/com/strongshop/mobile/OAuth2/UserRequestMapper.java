package com.strongshop.mobile.OAuth2;

import com.strongshop.mobile.dto.User.UserRequestDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


//OAuth2Attribute 에서 Attribute 생성 한 형식을 이용해서, RequestDto 로 변환
@Component
public class UserRequestMapper {
    public UserRequestDto toRequestDto(OAuth2User oAuth2User){
        var attributes = oAuth2User.getAttributes();
        return UserRequestDto.builder()
                .id((Long) attributes.get("id"))
                .nickname((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .profileImage((String) attributes.get("profile_image"))
                .thumbnailImage((String) attributes.get("thumbnail_image"))
                .build();

    }

}
