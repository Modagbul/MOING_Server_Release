package com.moing.backend.domain.missionHeart.domain.service;

import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.domain.missionHeart.domain.repository.MissionHeartRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionHeartQueryService {

    private final MissionHeartRepository missionHeartRepository;

    public boolean isAlreadyHeart(Long memberId, Long archiveId) {
        return missionHeartRepository.findAlreadyHeart(memberId, archiveId);
    }
    public MissionHeart findMissionHeartById(Long memberId, Long archiveId) {
        return missionHeartRepository.findByMemberIdAndArchiveId(memberId, archiveId);
    }
}
