package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Product.Item;
import com.strongshop.mobile.dto.Product.ProductRequestDto;
import com.strongshop.mobile.dto.Product.ProductResponseDto;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final CompanyRepository companyRepository;
    private final ProductService productService;

    //제품 등록
    @PostMapping("/api/product/register/{company_id}/{item}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> registerBlackbox(@RequestBody ProductRequestDto requestDto,@PathVariable("company_id") Long companyId, @PathVariable("item") String item){

        switch (item){
            case "blackbox":
                requestDto.setItem(Item.BLACKBOX);
                break;
            case "tinting":
                requestDto.setItem(Item.TINTING);
                break;
            case "ppf":
                requestDto.setItem(Item.PPF);
                break;
            default:
                break;
        }
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()->new RuntimeException());

        requestDto.setCompany(company);
        ProductResponseDto responseDto = productService.registerProduct(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    //제품 수정
    @PutMapping("/api/product/update/{item}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(@RequestBody ProductRequestDto requestDto,@PathVariable("item") String item){

        switch (item){
            case "blackbox":
                requestDto.setItem(Item.BLACKBOX);
                break;
            case "tinting":
                requestDto.setItem(Item.TINTING);
                break;
            case "ppf":
                requestDto.setItem(Item.PPF);
                break;
            default:
                break;
        }
        ProductResponseDto responseDto = productService.updateProduct(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto),HttpStatus.OK);
    }

    //제품 조회
        //전체조회(회사 id)
    @GetMapping("/api/product/get/{company_id}")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProductsByCompany(@PathVariable("company_id") Long company_id )
    {

        List<ProductResponseDto> responseDtos = productService.getAllProductsByCompany(company_id);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos),HttpStatus.OK);
    }

    @GetMapping("/api/product/get/{company_id}/{item}")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getSpecificItemsByCompany(@PathVariable("company_id") Long company_id,@PathVariable("item") String inputItem )
    {
        Item item;
        switch (inputItem){
            case "blackbox":
                item = Item.BLACKBOX;
                break;
            case "tinting":
                item = Item.TINTING;
                break;
            case "ppf":
                item = Item.PPF;
                break;
            default:
                item = null;
                break;
        }

        List<ProductResponseDto> responseDtos = productService.getSpecificItemsByCompany(company_id,item);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos),HttpStatus.OK);
    }

}
