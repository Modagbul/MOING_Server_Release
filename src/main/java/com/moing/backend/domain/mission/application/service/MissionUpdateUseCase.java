package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import com.moing.backend.domain.mission.exception.NoAccessCreateMission;
import com.moing.backend.domain.mission.exception.NoAccessDeleteMission;
import com.moing.backend.domain.mission.exception.NoAccessUpdateMission;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionUpdateUseCase {

    private final MissionSaveService missionSaveService;
    private final MissionQueryService missionQueryService;
    private final MemberGetService memberGetService;

    public MissionCreateRes updateMission(String userSocialId, Long missionId, MissionReq missionReq) {


        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission mission = missionQueryService.findMissionById(missionId);

        /**
         *  미션 생성자 확인
         */

        if (!member.getMemberId().equals(mission.getMakerId())) {
            throw new NoAccessUpdateMission();
        }
        mission.updateMission(missionReq);

        return MissionMapper.mapToMissionCreateRes(mission);

    }

    public MissionReadRes updateMissionStatus(String userSocialId, Long missionId) {


        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission findMission = missionQueryService.findMissionById(missionId);
        Team team = findMission.getTeam();

        if (findMission.getMakerId().equals(member.getMemberId())) {
            findMission.updateStatus(MissionStatus.END);
            findMission.updateDueTo(LocalDateTime.now());
        } else {
            throw new NoAccessUpdateMission();
        }

        return MissionMapper.mapToMissionReadRes(findMission);

    }
}
