package com.moing.backend.domain.mission.domain.repository;

import com.moing.backend.domain.mission.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long>,MissionCustomRepository {
}
