package com.moing.backend.domain.mission.domain.service;

import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionDeleteService {

    private final MissionRepository missionRepository;

    public void deleteMission(Long missionId) {
        missionRepository.deleteById(missionId);
    }
}
