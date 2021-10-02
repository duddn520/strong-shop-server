package com.strongshop.mobile.controller;

import com.strongshop.mobile.dto.Product.Item.*;
import com.strongshop.mobile.dto.Product.ProductRequestDto;
import com.strongshop.mobile.dto.Product.ProductResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Product.BlackboxService;
import com.strongshop.mobile.service.Product.PpfService;
import com.strongshop.mobile.service.Product.TintingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {



    //제품 등록
    @PostMapping("/api/product/blackbox")
    public ResponseEntity<ApiResponse<ProductResponseDto>> registerBlackbox(@RequestBody ProductRequestDto requestDto){

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

    //제품 수정
    @PutMapping("/api/product/blackbox")
    public ResponseEntity<ApiResponse<BlackboxResponseDto>> updateBlackbox(@RequestBody BlackboxRequestDto requestDto){

        BlackboxResponseDto responseDto = blackboxService.updateBlackbox(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto),HttpStatus.OK);
    }

    @PutMapping("/api/product/ppf")
    public ResponseEntity<ApiResponse<PpfResponseDto>> updatePpf(@RequestBody PpfRequestDto requestDto){

        PpfResponseDto responseDto = ppfService.updatePpf(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto),HttpStatus.OK);
    }

    @PutMapping("/api/product/tinting")
    public ResponseEntity<ApiResponse<TintingResponseDto>> updateTinting(@RequestBody TintingRequestDto requestDto)
    {
        TintingResponseDto responseDto = tintingService.updateTinting(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto),HttpStatus.OK);
    }

    //제품 조회
    @GetMapping("/api/product/blackbox")
    public ResponseEntity<ApiResponse<List<BlackboxResponseDto>>> getBlackboxesByCompany(@RequestParam("company_id") Long company_id )
    {
        List<BlackboxResponseDto> responseDtos = blackboxService.getBlackboxByCompany(company_id);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos),HttpStatus.OK);
    }

    @GetMapping("/api/product/ppf")
    public ResponseEntity<ApiResponse<List<PpfResponseDto>>> getPpfesByCompany(@RequestParam("company_id") Long company_id )
    {
        List<PpfResponseDto> responseDtos = ppfService.getPpfByCompany(company_id);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos),HttpStatus.OK);
    }

    @GetMapping("/api/product/tinting")
    public ResponseEntity<ApiResponse<List<TintingResponseDto>>> getTintingesByCompany(@RequestParam("company_id") Long company_id )
    {
        List<TintingResponseDto> responseDtos = tintingService.getTintingByCompany(company_id);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos),HttpStatus.OK);
    }
}
