package com.strongshop.mobile.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Company.CompanyInfo;
import com.strongshop.mobile.domain.Company.CompanyRepository;
import com.strongshop.mobile.domain.Contract.Contract;
import com.strongshop.mobile.domain.Gallery.Gallery;
import com.strongshop.mobile.domain.Image.ConstructionImageUrl;
import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import com.strongshop.mobile.domain.Image.InspectionImageUrl;
import com.strongshop.mobile.domain.Image.ReviewImageUrl;
import com.strongshop.mobile.domain.Order.Order;
import com.strongshop.mobile.domain.Review.Review;
import com.strongshop.mobile.domain.User.LoginMethod;
import com.strongshop.mobile.domain.User.Role;
import com.strongshop.mobile.domain.User.User;
import com.strongshop.mobile.domain.User.UserRepository;
import com.strongshop.mobile.dto.Company.CompanyRequestDto;
import com.strongshop.mobile.dto.Company.CompanyResponseDto;
import com.strongshop.mobile.dto.User.UserResponseDto;
import com.strongshop.mobile.jwt.JwtTokenProvider;
import com.strongshop.mobile.model.ApiResponse;
import com.strongshop.mobile.model.HttpResponseMsg;
import com.strongshop.mobile.model.HttpStatusCode;
import com.strongshop.mobile.service.BiddingService;
import com.strongshop.mobile.service.Company.CompanyService;
import com.strongshop.mobile.service.ContractService;
import com.strongshop.mobile.service.FileUploadService;
import com.strongshop.mobile.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CompanyController {

    private final CompanyService companyService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    private final ContractService contractService;
    private final OrderService orderService;
    private final BiddingService biddingService;


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

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();      //ContextHolder ?????? company ??????(??? ?????? ??????)
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
        String fcmToken = request.getHeader("FCM");

        HashMap<String, Object> companyInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //    ????????? ????????? Header??? ????????? ??????
            conn.setRequestProperty("Authorization", accessToken);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
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
                log.debug("email: {} already signed in user account.(CompanyController)",finduser.getEmail());
                return new ResponseEntity<>(ApiResponse.response(
                        HttpStatusCode.FORBIDDEN,
                        HttpResponseMsg.SEND_FAILED), HttpStatus.FORBIDDEN);
            }

            if(findcompany.getEmail()==null) {
                CompanyRequestDto requestDto = new CompanyRequestDto();
                requestDto.setEmail((String) companyInfo.get("email"));
                requestDto.setLoginMethod(LoginMethod.KAKAO);

                return new ResponseEntity<>(ApiResponse.response(       //???????????? ?????? ??????, ????????? ???????????? ?????? ????????????, ?????? ???????????? ?????? ??????.
                        HttpStatusCode.CREATED,
                        HttpResponseMsg.GET_SUCCESS,
                        new CompanyResponseDto(requestDto.toEntity())), HttpStatus.OK);
            }
            else
            {
                findcompany.updateFcmToken(fcmToken);

                CompanyResponseDto responseDto = new CompanyResponseDto(companyRepository.save(findcompany));

                String token = jwtTokenProvider.createToken(findcompany.getEmail(), Role.COMPANY, fcmToken);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Auth",token);

                return new ResponseEntity<>(ApiResponse.response(           //?????? ???????????? ??????, ????????? jwt ?????? ???, ?????????????????? ??????.
                        HttpStatusCode.OK,
                        HttpResponseMsg.POST_SUCCESS,
                        responseDto),headers,HttpStatus.OK);
            }
        } catch (IOException e) {
            log.error("IOException (CompanyController.companyLoginKakao)");
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/api/login/company/kakao")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> completeCompanyLoginKakao(@RequestBody CompanyRequestDto requestDto,HttpServletRequest request)
    {
        if(companyRepository.findByBusinessNumber(requestDto.getBusinessNumber()).isPresent())
        {
            log.debug("businessNumber: {} already signed up. (CompanyController.completeCompanyLoginKakao)",requestDto.getBusinessNumber());
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.LOGIN_FAIL
                    ),HttpStatus.FORBIDDEN);
        }

        if(requestDto.getEmail()!=null && requestDto.getBusinessNumber()!= null)       //???????????? ??? ?????? ????????? ?????? ??????.
        {
            Company company = requestDto.toEntity();
            CompanyResponseDto responseDto= new CompanyResponseDto(companyRepository.save(company));
            String token = jwtTokenProvider.createToken(company.getEmail(), Role.COMPANY,requestDto.getFcmToken());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth",token);

            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.POST_SUCCESS,
                    responseDto),headers,HttpStatus.OK);
        }
        else
        {
            log.error("email: {}, businessNumber: {} email or BN is null. (CompanyController.completeCompanyLoginKakao)",requestDto.getEmail(),requestDto.getBusinessNumber());
            throw new RuntimeException();
        }
    }

    @GetMapping("/api/login/company/naver")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> companyLoginNaver(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        String fcmToken = request.getHeader("FCM");
        String header = "Bearer " + accessToken;

        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        Map<String, Object> userInfo = new HashMap<>();
        requestHeaders.put("Authorization", header);

        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    ????????? ????????? Header??? ????????? ??????
            conn.setRequestProperty("Authorization", accessToken);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();

            String email = response.getAsJsonObject().get("email").getAsString() + "/";

            userInfo.put("email", email);

            User finduser = userRepository.findByEmail(email).orElseGet(() -> new User());
            Company findcompany = companyRepository.findByEmail(email).orElseGet(() -> new Company());
            if (finduser.getEmail()!=null && finduser.getEmail().equals(email)) {
                log.debug("email: {} already signed in user account. (CompanyController.companyLoginNaver)",finduser.getEmail());
                return new ResponseEntity<>(ApiResponse.response(
                        HttpStatusCode.NOT_ACCEPTABLE,
                        HttpResponseMsg.SEND_FAILED), HttpStatus.NOT_ACCEPTABLE);   //????????? ?????? ????????? ??????????????? ??????.
            }
            if (findcompany.getEmail()== null) {
                CompanyRequestDto requestDto = new CompanyRequestDto();
                requestDto.setEmail((String) userInfo.get("email"));
                requestDto.setLoginMethod(LoginMethod.NAVER);

                return new ResponseEntity<>(ApiResponse.response(       //???????????? ?????? ??????, ????????? ???????????? ?????? ????????????, ?????? ????????? ?????? ??????.
                        HttpStatusCode.CREATED,
                        HttpResponseMsg.GET_SUCCESS,
                        new CompanyResponseDto(requestDto.toEntity())), HttpStatus.OK);
            } else {
                findcompany.updateFcmToken(fcmToken);
                CompanyResponseDto responseDto = new CompanyResponseDto(companyRepository.save(findcompany));

                String token = jwtTokenProvider.createToken(findcompany.getEmail(), Role.COMPANY,fcmToken);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Auth", token);

                return new ResponseEntity<>(ApiResponse.response(           //?????? ???????????? ??????, ????????? jwt ?????? ???, ?????????????????? ??????.
                        HttpStatusCode.OK,
                        HttpResponseMsg.POST_SUCCESS,
                        responseDto), headers, HttpStatus.OK);
            }
        } catch(IOException e){
            log.error("IOException (CompanyController.companyLoginNaver)");
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/api/login/company/naver")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> completeCompanyLoginNaver(@RequestBody CompanyRequestDto requestDto)
    {
        if(companyRepository.findByBusinessNumber(requestDto.getBusinessNumber()).isPresent())
        {
            log.debug("businessNumber: {} already signed up. (CompanyController.completeCompanyLoginNaver)",requestDto.getBusinessNumber());
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.FORBIDDEN,
                    HttpResponseMsg.LOGIN_FAIL
            ),HttpStatus.FORBIDDEN);
        }

        if(requestDto.getEmail()!=null && requestDto.getBusinessNumber()!= null)       //???????????? ??? ?????? ????????? ?????? ??????.
        {
            Company company = requestDto.toEntity();
            CompanyResponseDto responseDto = new CompanyResponseDto(companyRepository.save(company));

            String token = jwtTokenProvider.createToken(company.getEmail(), Role.COMPANY,requestDto.getFcmToken());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth",token);

            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.POST_SUCCESS,
                    responseDto),headers,HttpStatus.OK);
        }
        else
        {
            log.error("email: {}, businessNumber: {} email or BN is null.(CompanyController.completeCompanyLoginNaver)",requestDto.getEmail(),requestDto.getBusinessNumber());
            throw new RuntimeException();
        }
    }

    @DeleteMapping("/api/company")
    @Transactional
    public ResponseEntity<ApiResponse> withdrawCompany(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        List<Gallery> galleries = company.getGalleries();                       //????????? ??????????????? ????????? ????????? ?????? ?????? ????????? ????????????.
        for(Gallery g : galleries)
        {
            List<GalleryImageUrl> imageUrls = g.getImageUrls();

            for(GalleryImageUrl img : imageUrls)
            {
                fileUploadService.removeFile(img.getFilename());
            }
        }
        if(company.getCompanyInfo().getBackgroundImageUrl()!=null)
            fileUploadService.removeFile(company.getCompanyInfo().getBackgroundFilename());

        List<Review> reviews = company.getReviews();
        for(Review r : reviews)
        {
            List<ReviewImageUrl> imageUrls = r.getReviewImageUrls();

            for(ReviewImageUrl img : imageUrls)
            {
                fileUploadService.removeFile(img.getFilename());
            }
        }

        List<Contract> contracts = contractService.getContractsByCompanyId(company.getId());

        if(contracts.isEmpty()) {
            companyService.deleteCompany(company);

            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.DELETE_SUCCESS),HttpStatus.OK);
        }
        else
        {
            log.debug("companyId: {} cannot withdraw (having ongoing contract). (CompanyController.withdrawCompany)",company.getId());
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.NOT_ACCEPTABLE,
                    HttpResponseMsg.DELETE_FAIL), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("/api/logout/company")
    public ResponseEntity<ApiResponse> companyLogout(HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));
        Company company = companyService.getCompanyByEmail(email);

        companyService.removeFcmToken(company);

        return new ResponseEntity<>(ApiResponse.response(
                HttpStatusCode.OK,
                HttpResponseMsg.DELETE_SUCCESS),HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/api/login/test/company")         //??????????????????
    public ResponseEntity<ApiResponse<CompanyResponseDto>> companyTestLoginKakao(HttpServletRequest request)
    {
        String fcmToken = request.getHeader("FCM");

        Company findcompany = companyRepository.findByEmail("companytestemail@strongshop.com").orElseGet(()->new Company());

        if (findcompany.getEmail()!=null)
        {
            String token = jwtTokenProvider.createToken(findcompany.getEmail(),Role.COMPANY,fcmToken);

            findcompany.updateFcmToken(fcmToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth", token);


            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.GET_SUCCESS,
                    new CompanyResponseDto(findcompany)), headers, HttpStatus.OK);
        }
        else
        {
            Company company = Company.builder()
                    .name("???????????????")
                    .businessNumber("7777777777")
                    .email("companytestemail@strongshop.com")
                    .fcmToken(fcmToken)
                    .loginMethod(LoginMethod.KAKAO)
                    .region("??????,??????")
                    .build();

            String token = jwtTokenProvider.createToken(company.getEmail(),Role.COMPANY,fcmToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Auth", token);
            System.out.println("token = " + token);

            CompanyResponseDto responseDto = new CompanyResponseDto(companyRepository.save(company));

            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.CREATED,
                    HttpResponseMsg.POST_SUCCESS,
                    responseDto), headers, HttpStatus.CREATED);
        }

    }

    @DeleteMapping("/api/company/{id}")
    @Transactional
    public ResponseEntity<ApiResponse> forcedWithdrawalOfCompany(@PathVariable(name = "id")Long id, HttpServletRequest request)
    {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.getToken(request));

        if(email.equals("companytestemail@strongshop.com")) {
            Company company = companyService.getCompanyById(id);

            List<Gallery> galleries = company.getGalleries();                       //????????? ??????????????? ????????? ????????? ?????? ?????? ????????? ????????????.
            for (Gallery g : galleries) {
                List<GalleryImageUrl> imageUrls = g.getImageUrls();

                for (GalleryImageUrl img : imageUrls) {
                    fileUploadService.removeFile(img.getFilename());
                }
            }
            if (company.getCompanyInfo().getBackgroundImageUrl() != null)
                fileUploadService.removeFile(company.getCompanyInfo().getBackgroundFilename());

            List<Review> reviews = company.getReviews();
            for (Review r : reviews) {
                List<ReviewImageUrl> imageUrls = r.getReviewImageUrls();

                for (ReviewImageUrl img : imageUrls) {
                    fileUploadService.removeFile(img.getFilename());
                }
            }

            List<Contract> contracts = contractService.getContractsByCompanyId(company.getId());

            for(Contract c : contracts)
            {
                Order order = c.getOrder();
                Bidding bidding = c.getBidding();

                List<InspectionImageUrl> inspectionImageUrls = c.getInspectionImageUrls();
                List<ConstructionImageUrl> constructionImageUrls = c.getConstructionImageUrls();

                for(InspectionImageUrl i : inspectionImageUrls)
                {
                    fileUploadService.removeFile(i.getFilename());
                }

                for(ConstructionImageUrl ci : constructionImageUrls)
                {
                    fileUploadService.removeFile(ci.getFilename());
                }

                contractService.deleteContract(c);
                orderService.deleteOrder(order);
                biddingService.deleteBidding(bidding);
            }

            companyService.deleteCompany(company);

            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.OK,
                    HttpResponseMsg.DELETE_SUCCESS),HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(ApiResponse.response(
                    HttpStatusCode.NOT_ACCEPTABLE,
                    HttpResponseMsg.DELETE_FAIL), HttpStatus.NOT_ACCEPTABLE);
        }

    }

}