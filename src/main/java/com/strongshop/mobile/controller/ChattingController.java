package com.strongshop.mobile.controller;

import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.firebase.FirebaseCloudMessageService;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ChattingController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ContractService contractService;

    @PutMapping("/api/chat/{contract_id}")
    public ResponseEntity<ApiResponse> chatAlarm(@PathVariable("contract_id") Long contractId,@RequestParam("content") String content ,HttpServletRequest request)
    {
        Role role = Role.valueOf((String) jwtTokenProvider.getRole(jwtTokenProvider.getToken(request)));
        Contract contract = contractService.getContractById(contractId);

        if(role.equals(Role.USER))
        {
            String fcm = contract.getBidding().getCompany().getFcmToken();
            try {
                firebaseCloudMessageService.sendMessageTo(fcm,contract.getOrder().getUser().getNickname(),content,"002");
            }catch (IOException e)
            {
                return new ResponseEntity<>(ApiResponse.response(
                        HttpStatusCode.INTERNAL_SERVER_ERROR,
                        HttpResponseMsg.SEND_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            String fcm = contract.getOrder().getUser().getFcmToken();
            try {
                firebaseCloudMessageService.sendMessageTo(fcm,contract.getBidding().getCompany().getName(),content,"002");
            }catch (IOException e)
            {
                return new ResponseEntity<>(ApiResponse.response(
                        HttpStatusCode.INTERNAL_SERVER_ERROR,
                        HttpResponseMsg.SEND_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.SEND_SUCCESS), HttpStatus.OK);
    }
}
