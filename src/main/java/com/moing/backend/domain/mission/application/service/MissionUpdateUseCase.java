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
import com.moing.backend.domain.mission.exception.NoAccessUpdateMission;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        Team team = mission.getTeam();

        Long memberId = member.getMemberId();

        if (!((memberId.equals(mission.getMakerId())) || memberId.equals(team.getLeaderId())) ) {
            throw new NoAccessUpdateMission();
        }
        mission.updateMission(missionReq);

        return MissionMapper.mapToMissionCreateRes(mission,member);

    }

    public MissionReadRes terminateMissionByUser(String userSocialId, Long missionId) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Long memberId = member.getMemberId();

        Mission findMission = missionQueryService.findMissionById(missionId);
        Team team = findMission.getTeam();

        if ((memberId.equals(findMission.getMakerId())) || memberId.equals(team.getLeaderId()) ) {
            findMission.updateStatus(MissionStatus.END);
            findMission.updateDueTo(LocalDateTime.now());
        } else {
            throw new NoAccessUpdateMission();
        }

        return MissionMapper.mapToMissionReadRes(findMission,member);

    }

    public void terminateMissionByAdmin() {
        missionQueryService.findMissionByDueTo().stream().forEach(
                mission -> mission.updateStatus(MissionStatus.END)
        );
    }
}
