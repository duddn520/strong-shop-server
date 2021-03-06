package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Contract.CompletedContract;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.dto.Contract.CompletedContractResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CompletedContractController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/api/completedcontract")
    @Transactional
    public ResponseEntity<ApiResponse<List<CompletedContractResponseDto>>> getAllContractHistories(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        User user = userService.getUserByEmail(email);

        List<CompletedContract> contracts = user.getCompletedContracts();
        List<CompletedContractResponseDto> responseDtos = new ArrayList<>();
        for(CompletedContract c : contracts)
        {
            CompletedContractResponseDto responseDto = new CompletedContractResponseDto(c);
            responseDtos.add(responseDto);
        }

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.GET_SUCCESS,
                responseDtos), HttpStatus.OK);
    }

}
