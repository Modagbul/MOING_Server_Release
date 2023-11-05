package com.moing.backend.domain.missionState.domain.service;

import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.missionState.domain.repository.MissionStateRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionStateDeleteService {

    private final MissionStateRepository missionStateRepository;

    public void deleteMissionState(List<MissionState> missionStates) {

        missionStateRepository.deleteAll(missionStates);
    }
}
