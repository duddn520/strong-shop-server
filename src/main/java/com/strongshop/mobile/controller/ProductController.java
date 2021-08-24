package com.strongshop.mobile.controller;

import com.strongshop.mobile.dto.Product.*;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Product.BlackboxService;
import com.strongshop.mobile.service.Product.PpfService;
import com.strongshop.mobile.service.Product.TintingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final TintingService tintingService;
    private final BlackboxService blackboxService;
    private final PpfService ppfService;

    @PostMapping("/api/product/blackbox")
    public ResponseEntity<ApiResponse<BlackboxResponseDto>> registerBlackbox(@RequestBody BlackboxRequestDto requestDto){

        BlackboxResponseDto responseDto = blackboxService.registerBlackbox(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @PostMapping("/api/product/tinting")
    public ResponseEntity<ApiResponse<TintingResponseDto>> registerTinting(@RequestBody TintingRequestDto requestDto){

        TintingResponseDto responseDto = tintingService.registerTinting(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @PostMapping("/api/product/ppf")
    public ResponseEntity<ApiResponse<PpfResponseDto>> registerPpf(@RequestBody PpfRequestDto requestDto){

        PpfResponseDto responseDto = ppfService.registerPpf(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }
}
