package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;

public interface DAUProvider {
    Long getTodayStats();
    Long getYesterdayStats();
    DAUStatusType getSupportedType();
}
