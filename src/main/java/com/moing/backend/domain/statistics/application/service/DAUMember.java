package com.moing.backend.domain.statistics.application.service;

import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.statistics.domain.constant.DAUStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DAUMember implements DAUProvider {

    private final MemberGetService memberGetService;

    @Override
    public Long getTodayStats() {
        return memberGetService.getTodayNewMembers();
    }

    @Override
    public Long getYesterdayStats() {
        return memberGetService.getYesterdayNewMembers();
    }

    @Override
    public DAUStatusType getSupportedType() {
        return DAUStatusType.DAILY_MEMBER_COUNT;
    }
}
