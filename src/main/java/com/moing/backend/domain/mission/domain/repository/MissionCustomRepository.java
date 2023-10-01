package com.moing.backend.domain.mission.domain.repository;

public interface MissionCustomRepository {
    Long findMissionsCountByTeam(Long teamId);
}