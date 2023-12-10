package com.moing.backend.global.config.fcm.util;

import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.config.fcm.dto.event.SingleFcmEvent;
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
    public void onMultiFcmEvent(MultiFcmEvent event) {
        fcmService.sendMultipleDevices(new MultiRequest(event.getMemberIdAndTokens(), event.getTitle(), event.getBody(), event.getIdInfo(), event.getName(), event.getAlarmType(), event.getPath()));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onSingleFcmEvent(SingleFcmEvent event) {
        fcmService.sendSingleDevice(new SingleRequest(event.getRegistrationToken(), event.getTitle(), event.getBody(), event.getMemberId(), event.getIdInfo(), event.getName(), event.getAlarmType(), event.getPath()));
    }
}

