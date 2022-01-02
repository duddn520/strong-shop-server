package com.strongshop.mobile.firebase;
import com.sun.jdi.ObjectCollectedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import okhttp3.Headers;

import javax.mail.Header;
import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private Notification notification;
        private Map<String,Object> data;
        private Android android;
        private Apns apns;
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
