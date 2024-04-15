package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DAUOnceMission implements DAUProvider {
    private final MissionQueryService missionQueryService;

    @Override
    public Long getTodayStats() {
        return missionQueryService.getTodayOnceMissions();
    }

    @Override
    public Long getYesterdayStats() {
        return missionQueryService.getYesterdayOnceMissions();
    }

    @Override
    public DAUStatusType getSupportedType() {
        return DAUStatusType.DAILY_ONCE_MISSION_COUNT;
    }
}
