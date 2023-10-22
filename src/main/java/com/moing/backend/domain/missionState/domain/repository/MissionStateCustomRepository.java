package com.moing.backend.domain.missionState.domain.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface MissionStateCustomRepository {

    Long getCountsByMissionId(Long missionId) ;


}
