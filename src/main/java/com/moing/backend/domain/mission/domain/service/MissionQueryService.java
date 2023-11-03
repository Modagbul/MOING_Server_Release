package com.moing.backend.domain.mission.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.exception.*;
import com.moing.backend.domain.mission.exception.NotFoundMissionException;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.domain.service.TeamGetService;
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
    private final TeamGetService teamGetService;

    public Mission findMissionById(Long missionId) {
        return missionRepository.findById(missionId).orElseThrow(NotFoundMissionException::new);
    }

    public Long findMissionsCountByTeam(Long teamId) {
        return missionRepository.findMissionsCountByTeam(teamId);
    }

    public List<GatherRepeatMissionRes> findAllRepeatMission(Long memberId) {
        List<Long> teams = teamGetService.getTeamIdByMemberId(memberId);
        return missionRepository.findRepeatMissionByMemberId(memberId,teams).orElseThrow(NotFoundMissionException::new);
    }

    public List<GatherSingleMissionRes> findAllSingleMission(Long memberId) {
        List<Long> teams = teamGetService.getTeamIdByMemberId(memberId);
        return missionRepository.findSingleMissionByMemberId(memberId, teams).orElseThrow(NotFoundMissionException::new);
    }

    /**
     * 스케쥴러에서 한시간 단위로 실행
     * 현재 시간으로부터 1시간 이내 종료 되는 미션 리턴
     */
    public List<Mission> findMissionByDueTo() {
        return missionRepository.findMissionByDueTo().orElseThrow(NotFoundEndMissionException::new);
    }

    public List<Long> findOngoingRepeatMissions() {
        return missionRepository.findOngoingRepeatMissions().orElseThrow(NotFoundOngoingMissionException::new);
    }
}
