package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;

public class MissionArchiveDeleteService {

    private MissionArchiveRepository missionArchiveRepository;

    public void deleteMissionArchive(MissionArchive missionArchive) {
        missionArchiveRepository.delete(missionArchive);
    }
}
