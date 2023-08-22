package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.mission.domain.service.MissionDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionDeleteUseCase {

    private final MissionDeleteService missionDeleteService;

    public void deleteMission(Long missionId) {
        missionDeleteService.deleteMission(missionId);
    }

}
