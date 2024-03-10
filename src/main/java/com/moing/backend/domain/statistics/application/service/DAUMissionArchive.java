package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DAUMissionArchive implements DAUProvider {

    private final MissionArchiveQueryService missionArchiveQueryService;

    @Override
    public Long getTodayStats() {
        return missionArchiveQueryService.getTodayMissionArchives();
    }

    @Override
    public Long getYesterdayStats() {
        return missionArchiveQueryService.getYesterdayMissionArchives();
    }

    @Override
    public DAUStatusType getSupportedType() {
        return DAUStatusType.DAILY_MISSION_ARCHIVE_COUNT;
    }
}
