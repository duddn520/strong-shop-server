package com.strongshop.mobile.controller;

import com.google.protobuf.Api;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.Company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ChattingController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyService companyService;

    @Async
    public void chatAlarm(Role role,Company company) {

        if (role.equals(Role.COMPANY)) {
            try {
                firebaseCloudMessageService.sendMessageTo(company.getFcmToken(), "title", "message", "1010101");
            } catch (IOException e) {
                System.out.println("e.get = " + e.getMessage());
            }
        }
    }


    @PutMapping("/api/chat")
    public ResponseEntity<ApiResponse> Alarm(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        Role role = Role.valueOf((String) jwtTokenProvider.getRole(jwtTokenProvider.getToken(request)));

        chatAlarm(role,company);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.POST_SUCCESS), HttpStatus.OK);
    }
}
