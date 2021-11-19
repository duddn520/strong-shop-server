package com.strongshop.mobile.controller;

import com.google.gson.*;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.User.UserRequestDto;
import com.strongshop.mobile.dto.User.UserResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/user")
    public ResponseEntity<ApiResponse<UserResponseDto>> findUser()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException());

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                new UserResponseDto(user)),HttpStatus.FOUND);
    }

    @GetMapping("/api/login/user/kakao")
    public ResponseEntity<ApiResponse<UserResponseDto>> userLoginKakao(HttpServletRequest request)
    {
        String accessToken = request.getHeader("Authorization");

        HashMap<String, Object> userInfo = new HashMap<>();
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

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            JsonObject profile = kakao_account.getAsJsonObject().get("profile").getAsJsonObject();

            Long id = element.getAsJsonObject().get("id").getAsLong();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String thumbnail_image_url = profile.getAsJsonObject().get("thumbnail_image_url").getAsString();
            String profile_image_url = profile.getAsJsonObject().get("profile_image_url").getAsString();

            userInfo.put("id", id);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            userInfo.put("thumbnail_image_url", thumbnail_image_url);
            userInfo.put("profile_image_url", profile_image_url);

            User finduser = userRepository.findByEmail(email).orElseGet(() -> new User());
            Company findcompany = companyRepository.findByEmail(email).orElseGet(() -> new Company());
            if (findcompany.getEmail()!=null && findcompany.getEmail().equals(email)) {
                throw new RuntimeException("이미 업체로 등록된 계정입니다.");        //업체로 이미 등록된 이메일이면 거부.
            }
            if (finduser.getEmail()== null) {
                UserRequestDto requestDto = new UserRequestDto();
                requestDto.setId((Long) userInfo.get("id"));
                requestDto.setNickname((String) userInfo.get("nickname"));
                requestDto.setEmail((String) userInfo.get("email"));
                requestDto.setThumbnailImage((String) userInfo.get("thumbnail_image_url"));
                requestDto.setProfileImage((String) userInfo.get("profile_image_url"));

                return new ResponseEntity<>(ApiResponse.response(       //존재하지 않는 회원, 헤더에 아무것도 없이 리턴되며, 추가 로그인 요청 필요.
                        HttpStatusCode.CREATED,
                        HttpResponseMsg.GET_SUCCESS,
                        new UserResponseDto(requestDto.toEntity())), HttpStatus.OK);
            } else {
                UserResponseDto responseDto = new UserResponseDto(userRepository.save(finduser));

                String token = jwtTokenProvider.createToken(finduser.getEmail(), Role.USER);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Auth", token);

                return new ResponseEntity<>(ApiResponse.response(           //이미 존재하는 회원, 헤더에 jwt 발급 후, 회원정보까지 리턴.
                        HttpStatusCode.OK,
                           HttpResponseMsg.POST_SUCCESS,
                        responseDto), headers, HttpStatus.OK);
            }
            } catch(IOException e){
                throw new RuntimeException(e);
        }
    }

    @PostMapping("/api/login/user/kakao")
    public ResponseEntity<ApiResponse<UserResponseDto>> completeUserLoginKakao(@RequestBody UserRequestDto requestDto)
    {

        if(requestDto.getEmail()!=null && requestDto.getPhoneNumber()!= null)       //필수항목 중 가장 중요한 두개 검사.
        {
            User user = requestDto.toEntity();
            UserResponseDto responseDto = new UserResponseDto(userRepository.save(user));

            String token = jwtTokenProvider.createToken(user.getEmail(), Role.USER);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth",token);

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

    @DeleteMapping("/api/user")
    public ResponseEntity<ApiResponse> withdrawUser()
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException());
        userService.deleteUser(user);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS),HttpStatus.OK);
    }

    @PostMapping("/api/user/fcm")
    public ResponseEntity<ApiResponse> changeFcm(@RequestBody String token, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        user.updateFcmToken(token);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS),HttpStatus.OK);
    }
}


