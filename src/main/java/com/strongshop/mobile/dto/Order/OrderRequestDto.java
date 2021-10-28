package com.strongshop.mobile.dto.Order;

import com.google.gson.*;
import com.google.gson.internal.GsonBuildConfig;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.util.JSONUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private String jsonString;
}
