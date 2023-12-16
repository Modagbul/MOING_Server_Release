package com.moing.backend.global.config.fcm.util;

import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.application.service.SaveMultiAlarmHistoryUseCase;
import com.moing.backend.domain.history.application.service.SaveSingleAlarmHistoryUseCase;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.config.fcm.dto.event.SingleFcmEvent;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.dto.request.SingleRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmMessageUtil {

    private final FcmService fcmService;
    private final SaveSingleAlarmHistoryUseCase saveSingleAlarmHistoryUseCase;
    private final SaveMultiAlarmHistoryUseCase saveMultiAlarmHistoryUseCase;

    @Async
    @EventListener
    public void onMultiFcmEvent(MultiFcmEvent event) {
        if (event.getIdAndTokensByPush().isPresent() && !event.getIdAndTokensByPush().get().isEmpty()) {
            fcmService.sendMultipleDevices(new MultiRequest(event.getIdAndTokensByPush().get(), event.getTitle(), event.getBody(), event.getIdInfo(), event.getName(), event.getAlarmType(), event.getPath()));
        }
        if (event.getIdAndTokensBySave().isPresent() && !event.getIdAndTokensBySave().get().isEmpty()) {
            saveMultiAlarmHistoryUseCase.saveAlarmHistories(AlarmHistoryMapper.getMemberIds(event.getIdAndTokensBySave().get()), event.getIdInfo(), event.getTitle(), event.getBody(), event.getName(), event.getAlarmType(), event.getPath());
        }
    }

    @Async
    @EventListener
    public void onSingleFcmEvent(SingleFcmEvent event) {
        if (event.getMember().isFirePush() && !event.getMember().isSignOut()) { //알림 on, 로그아웃 안함
            fcmService.sendSingleDevice(new SingleRequest(event.getMember().getFcmToken(), event.getTitle(), event.getBody(), event.getMember().getMemberId(), event.getIdInfo(), event.getName(), event.getAlarmType(), event.getPath()));
        }
        saveSingleAlarmHistoryUseCase.saveAlarmHistory(event.getMember().getMemberId(), event.getIdInfo(), event.getTitle(), event.getBody(), event.getName(), event.getAlarmType(), event.getPath());
    }
}

