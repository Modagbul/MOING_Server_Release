package com.moing.backend.domain.missionState.domain.service;

import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionArchiveStateQueryService {

    private final MissionArchiveRepository missionArchiveRepository;

    public int stateCountByMissionId(Long missionId) {
        return missionArchiveRepository.getCountsByMissionId(missionId).intValue();
    }



}
