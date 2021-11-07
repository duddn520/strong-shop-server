package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Product.Item;
import com.strongshop.mobile.dto.Product.ProductRequestDto;
import com.strongshop.mobile.dto.Product.ProductResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final JwtTokenProvider jwtTokenProvider;

    private final ProductService productService;
    private final CompanyService companyService;

    //제품 등록
    @PostMapping("/api/product/register/{item}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> registerBlackbox(@RequestBody ProductRequestDto requestDto, @PathVariable("item") String item, HttpServletRequest request){

        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        requestDto.setCompany(company);

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
            case "battery":
                requestDto.setItem(Item.BATTERY);
                break;
            case"afterblow":
                requestDto.setItem(Item.AFTERBLOW);
                break;
            case"deafening":
                requestDto.setItem(Item.DEAFENING);
                break;
            case"wrapping":
                requestDto.setItem(Item.WRAPPING);
                break;
            case"glasscoating":
                requestDto.setItem(Item.GLASSCOATING);
                break;
            case"undercoating":
                requestDto.setItem(Item.UNDERCOATING);
                break;
            case"etc":
                requestDto.setItem(Item.ETC);
                break;
            default:
                break;
        }

        ProductResponseDto responseDto = productService.registerProduct(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    //제품 수정
    @PutMapping("/api/product/update/{item}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(@RequestBody ProductRequestDto requestDto,@PathVariable("item") String item, HttpServletRequest request){

        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        requestDto.setCompany(company);
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
            case "battery":
                requestDto.setItem(Item.BATTERY);
                break;
            case"afterblow":
                requestDto.setItem(Item.AFTERBLOW);
                break;
            case"deafening":
                requestDto.setItem(Item.DEAFENING);
                break;
            case"wrapping":
                requestDto.setItem(Item.WRAPPING);
                break;
            case"glasscoating":
                requestDto.setItem(Item.GLASSCOATING);
                break;
            case"undercoating":
                requestDto.setItem(Item.UNDERCOATING);
                break;
            case"etc":
                requestDto.setItem(Item.ETC);
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
    @GetMapping("/api/product")
    public ResponseEntity<ApiResponse<Map<String,List<ProductResponseDto>>>> getAllProductsByCompany(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        Long company_id = company.getId();

        Map<String,List<ProductResponseDto>> map = new HashMap<>();
        map.put("tinting",productService.getSpecificItemsByCompany(company_id,Item.TINTING));
        map.put("blackbox",productService.getSpecificItemsByCompany(company_id,Item.BLACKBOX));
        map.put("ppf",productService.getSpecificItemsByCompany(company_id,Item.PPF));
        map.put("battery",productService.getSpecificItemsByCompany(company_id,Item.BATTERY));
        map.put("afterblow",productService.getSpecificItemsByCompany(company_id,Item.AFTERBLOW));
        map.put("deafening",productService.getSpecificItemsByCompany(company_id,Item.DEAFENING));
        map.put("wrapping",productService.getSpecificItemsByCompany(company_id,Item.WRAPPING));
        map.put("glasscoating",productService.getSpecificItemsByCompany(company_id,Item.GLASSCOATING));
        map.put("undercoating",productService.getSpecificItemsByCompany(company_id,Item.UNDERCOATING));
        map.put("etc",productService.getSpecificItemsByCompany(company_id,Item.ETC));

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                map),HttpStatus.OK);
    }

    @DeleteMapping("/api/product")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestBody ProductRequestDto requestDto)
    {
        productService.deleteProduct(requestDto.getId());

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS)
                ,HttpStatus.OK);

    }


}
