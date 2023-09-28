package com.moing.backend.domain.mission.domain.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.exception.NotFoundMissionException;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionQueryService {

    private final MissionRepository missionRepository;

    public Mission findMissionById(Long missionId) {
        return missionRepository.findById(missionId).orElseThrow(NotFoundMissionException::new);
    }
}
