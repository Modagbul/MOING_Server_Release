package com.moing.backend.domain.missionState.domain.service;

import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.missionState.domain.repository.MissionStateRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import com.moing.backend.domain.missionState.exception.NotFoundMissionStateException;
import javax.transaction.Transactional;
import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionStateQueryService {

    private final MissionStateRepository missionStateRepository;

    public int stateCountByMissionId(Long missionId) {
        return missionStateRepository.getCountsByMissionId(missionId);
    }

    public List<MissionState> findByMissionId(List<Long> missionId) {
        return missionStateRepository.findByMissionId(missionId);
    }

    public List<MissionState> findFinishMission() {
        return missionStateRepository.findFinishMission().orElseThrow(NotFoundMissionStateException::new);
    }


}
