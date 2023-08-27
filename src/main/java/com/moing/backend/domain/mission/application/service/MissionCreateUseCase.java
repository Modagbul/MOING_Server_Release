package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import com.moing.backend.domain.team.domain.repository.TeamRepository;
import com.moing.backend.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCreateUseCase {

    private final MissionSaveService missionSaveService;
    private final TeamRepository teamRepository;
    private final MemberGetService memberGetService;

    public MissionCreateRes createMission(String userSocialId, Long teamId, MissionReq missionReq) {


        Member member = memberGetService.getMemberBySocialId(userSocialId);

        Mission mission = MissionMapper.mapToMission(missionReq, member, MissionType.valueOf(missionReq.getType()));
        // teamRepository 변경 예정
        mission.setTeam(teamRepository.findById(teamId).orElseThrow());

        missionSaveService.save(mission);

        return MissionMapper.mapToMissionCreateRes(mission);

    }


}
