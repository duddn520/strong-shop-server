package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Product.Item;
import com.strongshop.mobile.domain.Product.Product;
import com.strongshop.mobile.dto.Product.ProductRequestDto;
import com.strongshop.mobile.dto.Product.ProductResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ProductController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ProductService productService;
    private final CompanyService companyService;

    //제품 등록
    @PostMapping("/api/product/{item}")
    @Transactional
    public ResponseEntity<ApiResponse<ProductResponseDto>> registerBlackbox(@RequestBody ProductRequestDto requestDto, @PathVariable("item") String item, HttpServletRequest request){

        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

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

        Product product = Product.builder()
                .company(company)
                .item(requestDto.getItem())
                .additionalInfo(requestDto.getAdditionalInfo())
                .name(requestDto.getName())
                .build();

        company.getProducts().add(product);
        ProductResponseDto responseDto = new ProductResponseDto(product);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    //제품 수정
    @PutMapping("/api/product/{item}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(@RequestBody ProductRequestDto requestDto,@PathVariable("item") String item, HttpServletRequest request){

        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);
        Long productId = requestDto.getId();

        Product product = productService.getProductById(productId);

        Product newproduct = Product.builder()
                .company(company)
                .item(requestDto.getItem())
                .additionalInfo(requestDto.getAdditionalInfo())
                .name(requestDto.getName())
                .build();

        product.updateProduct(newproduct);
        ProductResponseDto responseDto = productService.registerProduct(product);
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

    @GetMapping("/api/product/{company_id}")
    public ResponseEntity<ApiResponse<Map<String,List<ProductResponseDto>>>> getAllProductsByCompany4User(@PathVariable("company_id")Long companyId)
    {
        Map<String,List<ProductResponseDto>> map = new HashMap<>();
        map.put("tinting",productService.getSpecificItemsByCompany(companyId,Item.TINTING));
        map.put("blackbox",productService.getSpecificItemsByCompany(companyId,Item.BLACKBOX));
        map.put("ppf",productService.getSpecificItemsByCompany(companyId,Item.PPF));
        map.put("battery",productService.getSpecificItemsByCompany(companyId,Item.BATTERY));
        map.put("afterblow",productService.getSpecificItemsByCompany(companyId,Item.AFTERBLOW));
        map.put("deafening",productService.getSpecificItemsByCompany(companyId,Item.DEAFENING));
        map.put("wrapping",productService.getSpecificItemsByCompany(companyId,Item.WRAPPING));
        map.put("glasscoating",productService.getSpecificItemsByCompany(companyId,Item.GLASSCOATING));
        map.put("undercoating",productService.getSpecificItemsByCompany(companyId,Item.UNDERCOATING));
        map.put("etc",productService.getSpecificItemsByCompany(companyId,Item.ETC));

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                map),HttpStatus.OK);
    }
}
