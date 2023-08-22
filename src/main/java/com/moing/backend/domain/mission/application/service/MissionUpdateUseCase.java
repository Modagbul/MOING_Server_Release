package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.req.MissionCreateReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.Team;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import com.moing.backend.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionUpdateUseCase {

    private final MissionSaveService missionSaveService;
    private final Team team = new Team();

    public MissionCreateRes updateMission(MissionCreateReq missionCreateReq) {

        Member member = SecurityUtils.getLoggedInUser();
        Mission mission = MissionMapper.mapToMission(missionCreateReq, team, member, MissionType.ONCE);
        missionSaveService.save(mission);

        return MissionMapper.mapToMissionCreateRes(mission);

    }

}
