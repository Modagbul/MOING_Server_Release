package com.moing.backend.domain.mission.domain.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionSaveService {

    private final MissionRepository missionRepository;

    public void save(Mission mission) {
        missionRepository.save(mission);
    }
}
