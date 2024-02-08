package com.moing.backend.domain.missionRead.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionRead.application.mapper.MissionReadMapper;
import com.moing.backend.domain.missionRead.domain.entity.MissionRead;
import com.moing.backend.domain.missionRead.domain.service.MissionReadSaveService;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateMissionReadUseCase {

    private final MissionReadSaveService missionReadSaveService;

    public void createMissionRead(Team team, Member member, Mission mission) {
        MissionRead missionRead = MissionReadMapper.toMissionRead(team, member);
        missionReadSaveService.saveMissionRead(mission, missionRead);
    }
}
