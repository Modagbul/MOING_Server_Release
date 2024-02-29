package com.moing.backend.global.config.fcm.service;

import com.google.firebase.messaging.*;
import com.moing.backend.global.config.fcm.dto.request.SingleRequest;
import com.moing.backend.global.config.fcm.dto.response.SingleResponse;
import com.moing.backend.global.config.fcm.exception.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SingleMessageSender implements MessageSender<SingleRequest, SingleResponse> {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    @Retryable(value = FirebaseMessagingException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void send(SingleRequest request){
        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .build();

        // Android Configuration
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setChannelId("FCM_Channel")
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .build())
                .build();

        // APNs Configuration
        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory("YOUR_CATEGORY") // Replace with your category
                        .setAlert(ApsAlert.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .build())
                        .build())
                .build();

        Map<String, String> additionalData = new HashMap<>();
        additionalData.put("path", request.getPath());
        additionalData.put("idInfo", request.getIdInfo());

        Message message = Message.builder()
                .setToken(request.getRegistrationToken())
                .setNotification(notification)
                .setAndroidConfig(androidConfig) // Applying Android configuration
                .setApnsConfig(apnsConfig) // Applying APNs configuration
                .putAllData(additionalData)
                .build();

        try {
            String response = firebaseMessaging.send(message);
            new SingleResponse(response);
        } catch (FirebaseMessagingException e) {
            throw ExceptionHandler.handleFirebaseMessagingException(e);
        }
    }
}
