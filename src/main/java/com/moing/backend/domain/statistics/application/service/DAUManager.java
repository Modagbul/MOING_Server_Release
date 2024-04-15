package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class DAUManager {

    private final Map<DAUStatusType, DAUProvider> serviceMap = new EnumMap<>(DAUStatusType.class);

    @Autowired
    public DAUManager(List<DAUProvider> services) {
        for (DAUProvider service : services) {
            serviceMap.put(service.getSupportedType(), service);
        }
    }

    public Long getTodayStats(DAUStatusType type) {
        DAUProvider service = serviceMap.get(type);
        if (service != null) {
            return service.getTodayStats();
        }
        throw new IllegalArgumentException("Unsupported DAUStatusType: " + type);
    }

    public Long getYesterdayStats(DAUStatusType type) {
        DAUProvider service = serviceMap.get(type);
        if (service != null) {
            return service.getYesterdayStats();
        }
        throw new IllegalArgumentException("Unsupported DAUStatusType: " + type);
    }
}
