package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;

import java.util.List;
import java.util.Optional;

public interface MissionCustomRepository {
    Long findMissionsCountByTeam(Long teamId);
    Optional<List<Mission>> findRepeatMissionByMemberId(Long teamId, MissionStatus status);

    }