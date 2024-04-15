package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DAURepeatMission implements DAUProvider {
    private final MissionQueryService missionQueryService;

    @Override
    public Long getTodayStats() {
        return missionQueryService.getTodayRepeatMissions();
    }

    @Override
    public Long getYesterdayStats() {
        return missionQueryService.getYesterdayRepeatMissions();
    }

    @Override
    public DAUStatusType getSupportedType() {
        return DAUStatusType.DAILY_REPEAT_MISSION_COUNT;
    }
}