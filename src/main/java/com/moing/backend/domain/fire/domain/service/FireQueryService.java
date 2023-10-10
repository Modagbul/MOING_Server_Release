package com.moing.backend.domain.fire.domain.service;

import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.repository.FireCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FireQueryService {

    private final FireCustomRepository fireCustomRepository;

    public Fire findMyRecentFire(Long memberId) {
        return fireCustomRepository.findMyRecentFire(Long, memberId);
    }

}
