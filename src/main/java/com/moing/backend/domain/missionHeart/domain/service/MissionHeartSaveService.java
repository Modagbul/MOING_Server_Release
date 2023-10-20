package com.moing.backend.domain.missionHeart.domain.service;

import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.domain.missionHeart.domain.repository.MissionHeartCustomRepository;
import com.moing.backend.domain.missionHeart.domain.repository.MissionHeartRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionHeartSaveService {

    private final MissionHeartRepository missionHeartRepository;

    public MissionHeart save(MissionHeart missionHeart) {
        return missionHeartRepository.save(missionHeart);
    }

}
