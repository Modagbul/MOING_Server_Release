package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.fire.domain.service.FireQueryService;
import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DAUFire implements DAUProvider {

    private final FireQueryService fireQueryService;

    @Override
    public Long getTodayStats() {
        return fireQueryService.getTodayFires();
    }

    @Override
    public Long getYesterdayStats() {
        return fireQueryService.getYesterdayFires();
    }

    @Override
    public DAUStatusType getSupportedType() {
        return DAUStatusType.DAILY_FIRE_COUNT;
    }
}