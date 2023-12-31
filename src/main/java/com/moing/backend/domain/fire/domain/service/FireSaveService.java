package com.moing.backend.domain.fire.domain.service;

import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.repository.FireCustomRepository;
import com.moing.backend.domain.fire.domain.repository.FireRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class FireSaveService {

    private final FireRepository fireRepository;

    public Fire save(Fire fire) {
        return fireRepository.save(fire);
    }
}
