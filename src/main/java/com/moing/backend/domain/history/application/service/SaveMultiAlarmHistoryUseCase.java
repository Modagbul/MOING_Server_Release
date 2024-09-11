package com.moing.backend.domain.history.application.service;

import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.service.AlarmHistorySaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveMultiAlarmHistoryUseCase {

    private final AlarmHistorySaveService alarmHistorySaveService;

    @Async
    public void saveAlarmHistories(List<Long> memberIds, String idInfo, String title, String body, String name, AlarmType alarmType, String path) {
        List<AlarmHistory> alarmHistories = AlarmHistoryMapper.getAlarmHistories(idInfo, memberIds, title, body, name, alarmType, path);
        alarmHistorySaveService.saveAlarmHistories(alarmHistories);
    }
}
