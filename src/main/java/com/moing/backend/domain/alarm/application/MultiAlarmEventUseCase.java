package com.moing.backend.domain.alarm.application;

import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.application.service.SaveMultiAlarmHistoryUseCase;
import com.moing.backend.global.config.fcm.dto.event.MultiFcmEvent;
import com.moing.backend.global.config.fcm.dto.request.MultiRequest;
import com.moing.backend.global.config.fcm.service.MultiMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MultiAlarmEventUseCase {

    private final MultiMessageSender multiMessageSender;
    private final SaveMultiAlarmHistoryUseCase saveMultiAlarmHistoryUseCase;

    @Async
    @EventListener
    public void onMultiAlarmEvent(MultiFcmEvent event) {
        if (event.getIdAndTokensByPush().isPresent() && !event.getIdAndTokensByPush().get().isEmpty()) {
            multiMessageSender.send(new MultiRequest(event.getIdAndTokensByPush().get(), event.getTitle(), event.getBody(), event.getIdInfo(), event.getName(), event.getAlarmType(), event.getPath()));
        }
        if (event.getIdAndTokensBySave().isPresent() && !event.getIdAndTokensBySave().get().isEmpty()) {
            saveMultiAlarmHistoryUseCase.saveAlarmHistories(AlarmHistoryMapper.getMemberIds(event.getIdAndTokensBySave().get()), event.getIdInfo(), event.getTitle(), event.getBody(), event.getName(), event.getAlarmType(), event.getPath());
        }
    }
}
