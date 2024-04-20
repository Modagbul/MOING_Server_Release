package com.moing.backend.domain.missionRead.domain.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionRead.domain.entity.MissionRead;
import com.moing.backend.domain.missionRead.domain.repository.MissionReadRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional
public class MissionReadSaveService {

    private final MissionReadRepository missionReadRepository;


    public void saveMissionRead(Mission mission, MissionRead missionRead) {
        List<MissionRead> existingMissionReads = missionReadRepository.findMissionReadByMissionAndMemberAndTeam(mission, missionRead.getMember(), missionRead.getTeam());

        if (existingMissionReads.isEmpty()) {
            missionRead.updateMission(mission);
            missionReadRepository.save(missionRead);
        }
    }
}
