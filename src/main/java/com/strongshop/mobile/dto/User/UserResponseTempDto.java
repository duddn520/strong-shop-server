package com.strongshop.mobile.dto.User;

import com.strongshop.mobile.domain.User.User;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserResponseTempDto {

    private final Long id;
    private final String email;

    public UserResponseTempDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

}
