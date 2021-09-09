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
    private String name;
    private String picture;

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
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    Map<String, Object> convertToMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",attributeKey);
        map.put("key",attributeKey);
        map.put("name",name);
        map.put("email",email);
        map.put("picture",picture);
        return map;
    }
}
