package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import com.moing.backend.domain.mission.exception.NoAccessCreateMission;
import com.moing.backend.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionUpdateUseCase {

    private final MissionSaveService missionSaveService;
    private final MissionQueryService missionQueryService;
    private final MemberGetService memberGetService;

    public MissionCreateRes updateMission(String userSocialId, Long missionId, MissionReq missionReq) {


        Member member = memberGetService.getMemberBySocialId(userSocialId);

        // 소모임장 확인 로직 추가
        Mission findMission = missionQueryService.findMissionById(missionId);
        findMission.updateMission(missionReq);

        return MissionMapper.mapToMissionCreateRes(findMission);

    }

    public MissionReadRes updateMissionStatus(String userSocialId, Long missionId) {


        Member member = memberGetService.getMemberBySocialId(userSocialId);

        Mission findMission = missionQueryService.findMissionById(missionId);

        if (findMission.getTeam().getLeaderId().equals(member.getMemberId())) {
            findMission.updateStatus(MissionStatus.END);
        } else {
            throw new NoAccessCreateMission();
        }

        return MissionMapper.mapToMissionReadRes(findMission);

    }
}
