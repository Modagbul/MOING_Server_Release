package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionGatherBoardUseCase {

    private final MissionQueryService missionQueryService;
    private final MemberGetService memberGetService;

    public List<GatherSingleMissionRes> getAllActiveSingleMissions(String userId) {
        Long memberId  = memberGetService.getMemberBySocialId(userId).getMemberId();
        return missionQueryService.findAllSingleMission(memberId);

    }



}
