package com.strongshop.mobile.firebase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validate_only;
    private Message message;
    private Android android;
    private Apns apns;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private Notification notification;
        private Map<String,Object> data;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification{
        private String title;
        private String body;
        private String image;
        private String sound;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android{
        private Notification notification;
        private String priority;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Apns{
        private Map<String,Object> headers;
        private boolean content_available;

    }
}
