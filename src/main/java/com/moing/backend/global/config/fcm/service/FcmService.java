package com.moing.backend.global.config.fcm.service;

import com.google.firebase.messaging.*;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.dto.request.SingleRequest;
import com.moing.backend.global.config.fcm.dto.response.MultiResponse;
import com.moing.backend.global.config.fcm.dto.response.SingleResponse;
import com.moing.backend.global.config.fcm.exception.NotificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;


    @Retryable(value = FirebaseMessagingException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public SingleResponse sendSingleDevice(SingleRequest toSingleRequest) {

        Notification notification = Notification.builder()
                .setTitle(toSingleRequest.getTitle())
                .setBody(toSingleRequest.getBody())
                .build();

        // Android Configuration
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setChannelId("FCM_Channel")
                        .setTitle(toSingleRequest.getTitle())
                        .setBody(toSingleRequest.getBody())
                        .build())
                .build();

        // APNs Configuration
        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory("YOUR_CATEGORY") // Replace with your category
                        .setAlert(ApsAlert.builder()
                                .setTitle(toSingleRequest.getTitle())
                                .setBody(toSingleRequest.getBody())
                                .build())
                        .build())
                .build();

        Message message = Message.builder()
                .setToken(toSingleRequest.getRegistrationToken())
                .setNotification(notification)
                .setAndroidConfig(androidConfig) // Applying Android configuration
                .setApnsConfig(apnsConfig) // Applying APNs configuration
                .build();

        try {
            String response = firebaseMessaging.send(message);
            return new SingleResponse(response);
        } catch (FirebaseMessagingException e) {
            throw handleException(e);
        }
    }


    @Retryable(value = FirebaseMessagingException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public MultiResponse sendMultipleDevices(MultiRequest toMultiRequest) {

        Notification notification = Notification.builder()
                .setTitle(toMultiRequest.getTitle())
                .setBody(toMultiRequest.getBody())
                .build();

        // Android Configuration
        AndroidConfig androidConfig = AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setChannelId("FCM_Channel")
                        .setTitle(toMultiRequest.getTitle())
                        .setBody(toMultiRequest.getBody())
                        .build())
                .build();

        // APNs Configuration
        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory("YOUR_CATEGORY") // Replace with your category
                        .setAlert(ApsAlert.builder()
                                .setTitle(toMultiRequest.getTitle())
                                .setBody(toMultiRequest.getBody())
                                .build())
                        .build())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(toMultiRequest.getRegistrationToken())
                .setNotification(notification)
                .setAndroidConfig(androidConfig) // Applying Android configuration
                .setApnsConfig(apnsConfig) // Applying APNs configuration
                .build();

        try {
            BatchResponse response = firebaseMessaging.sendMulticast(message);
            List<String> failedTokens = new ArrayList<>();

            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        // Add the failed tokens to the list
                        failedTokens.add(toMultiRequest.getRegistrationToken().get(i));
                    }
                }
            }

            String messageString = String.format("%d messages were sent successfully.", response.getSuccessCount());

            return new MultiResponse(messageString, failedTokens);

        } catch (FirebaseMessagingException e) {
            throw handleException(e);
        }
    }



    private NotificationException handleException(FirebaseMessagingException e) {
        String errorCode = e.getErrorCode().name();
        String errorMessage = e.getMessage();

        switch (errorCode) {
            case "INVALID_ARGUMENT":
                return new NotificationException("올바르지 않은 인자 값입니다: " + errorMessage);
            case "NOT_FOUND":
                return new NotificationException("등록 토큰이 유효하지 않거나, 주제(Topic)가 존재하지 않습니다: " + errorMessage);
            case "UNREGISTERED":
                return new NotificationException("해당 주제(Topic)의 구독이 해지되었습니다: " + errorMessage);
            case "UNAVAILABLE":
                return new NotificationException("서비스를 사용할 수 없습니다: " + errorMessage);
            default:
                return new NotificationException("메시지 전송에 실패했습니다: " + errorMessage);
        }
    }

}


