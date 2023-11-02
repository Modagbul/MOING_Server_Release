package com.moing.backend.domain.missionState.domain.service;

import com.moing.backend.domain.missionState.domain.repository.MissionStateRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionStateQueryService {

    private final MissionStateRepository missionStateRepository;

    public Long stateCountByMissionId(Long missionId) {
        return missionStateRepository.getCountsByMissionId(missionId);
    }


}
