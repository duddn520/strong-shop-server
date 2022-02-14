package com.strongshop.mobile.dto.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private String region;
    private String details;
}
