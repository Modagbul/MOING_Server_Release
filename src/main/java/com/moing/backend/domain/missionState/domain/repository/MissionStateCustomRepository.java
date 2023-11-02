package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionStateCustomRepository {

    Long getCountsByMissionId(Long missionId) ;


}
