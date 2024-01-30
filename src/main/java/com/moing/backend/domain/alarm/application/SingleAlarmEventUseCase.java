package com.moing.backend.domain.alarm.application;

import com.moing.backend.domain.history.application.service.SaveSingleAlarmHistoryUseCase;
import com.moing.backend.global.config.fcm.dto.event.SingleFcmEvent;
import com.moing.backend.global.config.fcm.dto.request.SingleRequest;
import com.moing.backend.global.config.fcm.service.SingleMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SingleAlarmEventUseCase {

    private final SingleMessageSender singleMessageSender;
    private final SaveSingleAlarmHistoryUseCase saveSingleAlarmHistoryUseCase;

    @Async
    @EventListener
    public void onSingleAlarmEvent(SingleFcmEvent event) {
        if (event.getMember().isFirePush() && !event.getMember().isSignOut()) { //알림 on, 로그아웃 안함
            singleMessageSender.send(new SingleRequest(event.getMember().getFcmToken(), event.getTitle(), event.getBody(), event.getMember().getMemberId(), event.getIdInfo(), event.getName(), event.getAlarmType(), event.getPath()));
        }
        saveSingleAlarmHistoryUseCase.saveAlarmHistory(event.getMember().getMemberId(), event.getIdInfo(), event.getTitle(), event.getBody(), event.getName(), event.getAlarmType(), event.getPath());
    }

}
