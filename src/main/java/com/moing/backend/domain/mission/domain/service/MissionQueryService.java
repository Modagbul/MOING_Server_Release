package com.moing.backend.domain.mission.domain.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.exception.NotFoundEndMissionException;
import com.moing.backend.domain.mission.exception.NotFoundMissionException;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Mission> findRepeatMissionByTeamId(Long teamId) {
        return missionRepository.findRepeatMissionByMemberId(teamId, MissionStatus.ONGOING).orElseThrow(NotFoundMissionException::new);
    }

    /**
     * 스케쥴러에서 한시간 단위로 실행
     * 현재 시간으로부터 1시간 이내 종료 되는 미션 리턴
     */
    public List<Mission> findMissionByDueTo() {
        return missionRepository.findMissionByDueTo().orElseThrow(NotFoundEndMissionException::new);
    }
}
