package com.moing.backend.domain.missionStatus.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionStatus.domain.entity.MissionStatus;
import com.moing.backend.domain.missionStatus.domain.entity.Status;
import com.moing.backend.domain.missionStatus.domain.repository.MissionStatusRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionStatusSaveService {

    private final MissionStatusRepository missionStatusRepository;

    public void saveMissionStatus(Member member, Mission mission, Status status) {
        missionStatusRepository.save(MissionStatus.builder()
                .mission(mission)
                .member(member)
                .status(status)
                .build());
    }

}
