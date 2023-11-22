package com.moing.backend.global.config.fcm.util;

import com.moing.backend.global.config.fcm.dto.event.FcmEvent;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.dto.request.SingleRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FcmMessageUtil {

    private final FcmService fcmService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onFcmMessageEvent(FcmEvent event) {
        if (event.getTokens().size() == 1) {
            fcmService.sendSingleDevice(new SingleRequest(event.getTitle(), event.getBody(), event.getTokens().get(0)));
        } else {
            fcmService.sendMultipleDevices(new MultiRequest(event.getTokens(), event.getTitle(), event.getBody()));
        }
    }
}

