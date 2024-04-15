package com.moing.backend.domain.fire.domain.service;

import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.domain.repository.FireRepository;
import com.moing.backend.domain.fire.exception.NotFoundFireReceiversException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FireQueryService {

    private final FireRepository fireRepository;

    public boolean hasFireCreatedWithinOneHour(Long throwMemberId, Long receiveMemberId) {
        return fireRepository.hasFireCreatedWithinOneHour(throwMemberId,receiveMemberId);
    }

    public List<FireReceiveRes> getNotYetMissionMember(Long teamId, Long missionId, Long memberId) {
        return fireRepository.getFireReceivers(teamId, missionId,memberId).orElseThrow(NotFoundFireReceiversException::new);
    }

    public Long getTodayFires(){
        return fireRepository.getTodayFires();
    }
    public Long getYesterdayFires(){
        return fireRepository.getYesterdayFires();
    }
}
