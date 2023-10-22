package com.moing.backend.domain.missionHeart.domain.repository;

import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MissionHeartCustomRepository {

    boolean findAlreadyHeart(Long memberId, Long archiveId);
    MissionHeart findByMemberIdAndArchiveId(Long memberId, Long archiveId);


    }
