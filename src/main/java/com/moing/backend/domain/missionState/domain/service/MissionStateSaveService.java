package com.moing.backend.domain.missionState.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.missionState.domain.entity.Status;
import com.moing.backend.domain.missionState.domain.repository.MissionStateRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionStateSaveService {

    private final MissionStateRepository missionStateRepository;

    public void saveMissionState(Member member, Mission mission, MissionStatus status) {
        missionStateRepository.save(MissionState.builder()
                .mission(mission)
                .member(member)
                .status(status)
                .build());
    }

}
