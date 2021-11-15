package com.strongshop.mobile.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.Company.CompanyRequestDto;
import com.strongshop.mobile.dto.Company.CompanyResponseDto;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class CompanyController {

    private final CompanyService companyService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;


    @PostMapping("/api/company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> registerCompany(@RequestBody CompanyRequestDto requestDto) {

        CompanyResponseDto responseDto = companyService.registerCompany(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.CREATED,
                HttpResponseMsg.POST_SUCCESS,
                responseDto), HttpStatus.CREATED);
    }

    @PutMapping("/api/company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> updateCompany(@RequestBody CompanyRequestDto requestDto){

        CompanyResponseDto responseDto = companyService.updateCompany(requestDto);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.UPDATE_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @GetMapping("/api/company")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> getCompany(){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();      //ContextHolder 기반 company 조회(내 회사 조회)
        String email = userDetails.getUsername();

        Company company = companyRepository.findByEmail(email).orElseThrow(()-> new RuntimeException());

        CompanyResponseDto responseDto = new CompanyResponseDto(company);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDto), HttpStatus.OK);
    }

    @GetMapping("/api/login/company/kakao")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> companyLoginKakao(HttpServletRequest request)
    {
        String accessToken = request.getHeader("Authorization");

        HashMap<String, Object> companyInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            Long id = element.getAsJsonObject().get("id").getAsLong();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            companyInfo.put("id",id);
            companyInfo.put("email", email);
            System.out.println("email = " + email);

            Company findcompany = companyRepository.findByEmail(email).orElseGet(()->new Company());
            User finduser = userRepository.findByEmail(email).orElseGet(()->new User());
            if(finduser.getEmail()!=null&&finduser.getEmail().equals(email)){
                throw new RuntimeException("이미 유저로 등록된 계정입니다.");        //유저로 이미 존재하는 이메일이면 가입 거부.
            }

            System.out.println("findcompany.getEmail() = " + findcompany.getEmail());

            if(findcompany.getEmail()==null) {
                CompanyRequestDto requestDto = new CompanyRequestDto();
                requestDto.setId((Long) companyInfo.get("id"));
                requestDto.setEmail((String) companyInfo.get("email"));

                return new ResponseEntity<>(ApiResponse.response(       //존재하지 않는 업체, 헤더에 아무것도 없이 리턴되며, 추가 회원가입 요청 필요.
                        HttpStatusCode.CREATED,
                        HttpResponseMsg.GET_SUCCESS,
                        new CompanyResponseDto(requestDto.toEntity())), HttpStatus.OK);
            }
            else
            {
                CompanyResponseDto responseDto = new CompanyResponseDto(companyRepository.save(findcompany));

                String token = jwtTokenProvider.createToken(findcompany.getEmail(), Role.COMPANY);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Auth",token);

                return new ResponseEntity<>(ApiResponse.response(           //이미 존재하는 업체, 헤더에 jwt 발급 후, 업체정보까지 리턴.
                        HttpStatusCode.OK,
                        HttpResponseMsg.POST_SUCCESS,
                        responseDto),headers,HttpStatus.OK);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/api/login/company/kakao")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> completeCompanyLoginKakao(@RequestBody CompanyRequestDto requestDto)
    {
        if(companyRepository.findByBusinessNumber(requestDto.getBusinessNumber()).isPresent())
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.LOGIN_FAIL
                    ),HttpStatus.FORBIDDEN);
        }

        if(requestDto.getEmail()!=null && requestDto.getBusinessNumber()!= null)       //필수항목 중 가장 중요한 두개 검사.
        {
            Company company = requestDto.toEntity();
            CompanyResponseDto responseDto= new CompanyResponseDto(companyRepository.save(company));
            String token = jwtTokenProvider.createToken(company.getEmail(), Role.COMPANY);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth",token);

            try{
                firebaseCloudMessageService.sendMessageTo(requestDto.getFcmToken(),"알림","123412341234");
            }
            catch(IOException e)
            {
                throw new RuntimeException("입출력 에러.");
            }

            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.POST_SUCCESS,
                    responseDto),headers,HttpStatus.OK);
        }
        else
        {
            throw new RuntimeException();
        }
    }

    @DeleteMapping("/api/company")
    public ResponseEntity<ApiResponse> withdrawCompany()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        Company company = companyRepository.findByEmail(email).orElseThrow(()->new RuntimeException());
        companyService.deleteCompany(company);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS),HttpStatus.OK);
    }
}
