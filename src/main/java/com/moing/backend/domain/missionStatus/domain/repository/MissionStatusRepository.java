package com.moing.backend.domain.missionStatus.domain.repository;

import com.moing.backend.domain.missionStatus.domain.entity.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionStatusRepository extends JpaRepository<MissionStatus, Long> {
}
