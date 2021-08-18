package com.strongshop.mobile.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyNotFoundException extends RuntimeException {
    private Long id;
}
