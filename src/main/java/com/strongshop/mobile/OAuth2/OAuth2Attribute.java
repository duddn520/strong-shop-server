package com.strongshop.mobile.OAuth2;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String nickname;
    private String profileImage;
    private String thumbnailImage;

    static OAuth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes){
        switch (provider){
            case "kakao":
                return ofKakao("email",attributes);
            default: throw new RuntimeException();
        }
    }

    private static  OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes){
        Map<String, Object> kakaoAccount = (Map<String,Object>) attributes.get("kakao_account");
        Map<String,Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .nickname((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profileImage((String) kakaoProfile.get("profile_image_url"))
                .thumbnailImage((String) kakaoProfile.get("thumbnail_image_url"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    Map<String, Object> convertToMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",attributeKey);
        map.put("key",attributeKey);
        map.put("nickname",nickname);
        map.put("email",email);
        map.put("profile_image",profileImage);
        map.put("thumbnail_image",thumbnailImage);
        return map;
    }
}
