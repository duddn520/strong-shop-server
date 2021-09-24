package com.strongshop.mobile.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserEmailNotFoundException extends RuntimeException {
    private String email;
}
