package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionCustomRepository {
    Long findMissionsCountByTeam(Long teamId);
    Optional<List<Mission>> findRepeatMissionByMemberId(Long teamId, MissionStatus status);

    Optional<List<GatherSingleMissionRes>> findMissionsByMemberId(Long memberId, List<Long> teams);


}