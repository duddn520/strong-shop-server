package com.strongshop.mobile.domain.Product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Getter
public enum Item {
    TINTING("tinting"),
    BLACKBOX("blackbox"),
    PPF("ppf"),
    BATTERY("battery"),
    AFTERBLOW("afterblow"),
    DEAFENING("deafening"),
    WRAPPING("wrapping"),
    GLASSCOATING("glasscoating"),
    UNDERCOATING("undercoating"),
    ETC("etc");

    private final String key;
}
