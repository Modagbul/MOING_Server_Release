package com.moing.backend.domain.history.application.service;

import com.moing.backend.domain.history.application.mapper.AlarmHistoryMapper;
import com.moing.backend.domain.history.domain.entity.AlarmHistory;
import com.moing.backend.domain.history.domain.entity.AlarmType;
import com.moing.backend.domain.history.domain.service.AlarmHistorySaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveSingleAlarmHistoryUseCase {

    private final AlarmHistoryMapper alarmHistoryMapper;
    private final AlarmHistorySaveService alarmHistorySaveService;

    @Async
    public void saveAlarmHistory(Long memberId, String idInfo, String title, String body, String name, AlarmType alarmType, String path) {
        AlarmHistory alarmHistory = alarmHistoryMapper.toAlarmHistory(alarmType, path, idInfo, memberId, title, body, name);
        alarmHistorySaveService.saveAlarmHistory(alarmHistory);
    }
}
