package com.strongshop.mobile.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    @Autowired
    ResourceLoader resourceLoader;

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/strongshop-3fc81/messages:send";
    private final ObjectMapper objectMapper;



    private String getAccessToken() throws IOException{
        Resource resource = resourceLoader.getResource("classpath:strongshop-3fc81-firebase-adminsdk-jypdg-8ae68bebe7.json");
        InputStream inputStream = resource.getInputStream();
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream)
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }

    @Async
    public void sendMessageTo(String targetToken, String title, String body, String index) throws IOException {
            String message = makeMessage(targetToken, title, body, index);

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();

            Response response = client.newCall(request)
                    .execute();

            System.out.println(response.body().string());
    }

    @Async
    public void sendMessageTo(String targetToken, String title, String body, String index, Long orderId) throws IOException {
        String message = makeMessage(targetToken, title, body, index,orderId);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();
    }



    private String makeMessage(String targetToken, String title, String body,String index) throws JsonProcessingException{
        Map<String,Object> map = new HashMap<>();
        map.put("index",index);
        map.put("time", LocalDateTime.now());
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .data(map)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String makeMessage(String targetToken, String title, String body, String index, Long orderId) throws JsonProcessingException
    {
        Map<String,Object> map = new HashMap<>();
        map.put("index",index);
        map.put("time", LocalDateTime.now());
        map.put("order_id",orderId);
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .data(map)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }
}
