package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionStateCustomRepository {

    int getCountsByMissionId(Long missionId) ;

    List<MissionState> findByMissionId(List<Long> missionId);

    Optional<List<MissionState>> findFinishMission();


}
