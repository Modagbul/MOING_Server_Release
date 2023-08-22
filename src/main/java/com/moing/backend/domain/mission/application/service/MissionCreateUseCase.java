package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.mapper.MissionMapper;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.Team;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionCreateUseCase {

    private final MissionSaveService missionSaveService;
    private final MemberRepository memberRepository;

    public MissionCreateRes createMission(MissionReq missionReq) {

        Team team = new Team();

//        Member member = SecurityUtils.getLoggedInUser();
        Member member = memberRepository.findById(1L).orElseThrow();
        Mission mission = MissionMapper.mapToMission(missionReq, team, member, MissionType.ONCE);
        missionSaveService.save(mission);

        return MissionMapper.mapToMissionCreateRes(mission);

    }


}
