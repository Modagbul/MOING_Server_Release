package com.moing.backend.domain.mission.domain.service;

import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.exception.NotFoundMissionException;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionQueryService {

    private final MissionRepository missionRepository;

    public Mission findMissionById(Long missionId) {
        return missionRepository.findById(missionId).orElseThrow(NotFoundMissionException::new);
    }

    public Long findMissionsCountByTeam(Long teamId) {
        return missionRepository.findMissionsCountByTeam(teamId);
    }

    public List<GatherRepeatMissionRes> findAllRepeatMission(Long memberId) {
        List<Long> teams = new ArrayList<>();
        teams.add(1L);
        teams.add(2L);
        return missionRepository.findRepeatMissionByMemberId(memberId,teams).orElseThrow(NotFoundMissionException::new);
    }

    public List<GatherSingleMissionRes> findAllSingleMission(Long memberId) {

        List<Long> teams = new ArrayList<>();
        teams.add(1L);
        teams.add(2L);
        return missionRepository.findSingleMissionByMemberId(memberId, teams).orElseThrow(NotFoundMissionException::new);
    }
}
