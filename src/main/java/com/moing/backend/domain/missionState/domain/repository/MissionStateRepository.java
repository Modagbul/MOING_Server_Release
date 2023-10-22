package com.moing.backend.domain.missionState.domain.repository;

import com.moing.backend.domain.missionState.domain.entity.MissionState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionStateRepository extends JpaRepository<MissionState, Long> {
}
