package com.moing.backend.global.config.fcm.service;

import com.google.firebase.messaging.*;
import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.dto.response.MultiResponse;
import com.moing.backend.global.config.fcm.exception.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MultiMessageSender implements MessageSender<MultiRequest, MultiResponse> {

    private final FirebaseMessaging firebaseMessaging;

    @Override
    @Retryable(value = FirebaseMessagingException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void send(MultiRequest request) {


        List<String> fcmTokens = AlarmHistoryMapper.getFcmTokens(request.getMemberIdAndTokens());
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

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(fcmTokens)
                .setNotification(notification)
                .setAndroidConfig(androidConfig) // Applying Android configuration
                .setApnsConfig(apnsConfig) // Applying APNs configuration
                .putAllData(additionalData)
                .build();

        try {
//            BatchResponse response = firebaseMessaging.sendMulticast(message);
            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);


            List<String> failedTokens = new ArrayList<>();

            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();

                List<Long> memberIds = AlarmHistoryMapper.getMemberIds(request.getMemberIdAndTokens());

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        // Add the failed tokens to the list
                        failedTokens.add(fcmTokens.get(i));
                    }
                }
            }

            String messageString = String.format("%d messages were sent successfully.", response.getSuccessCount());

            new MultiResponse(messageString, failedTokens);

        } catch (FirebaseMessagingException e) {
            throw ExceptionHandler.handleFirebaseMessagingException(e);
        }
    }

}
