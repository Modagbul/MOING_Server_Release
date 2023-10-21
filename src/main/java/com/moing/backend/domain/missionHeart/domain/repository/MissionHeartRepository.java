package com.moing.backend.domain.missionHeart.domain.repository;

import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionHeartRepository extends JpaRepository<MissionHeart,Long>,MissionHeartCustomRepository {
}
