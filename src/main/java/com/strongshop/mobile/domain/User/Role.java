package com.strongshop.mobile.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","일반 사용자"),
    DEALER("ROLE_DEALER","딜러"),
    COMPANY("ROLE_COMPANY","업체");

    private final String key;
    private final String title;
}
