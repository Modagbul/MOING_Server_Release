package com.moing.backend.domain.fire.application.service;

import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.application.mapper.FireMapper;
import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.repository.FireCustomRepository;
import com.moing.backend.domain.fire.domain.service.FireQueryService;
import com.moing.backend.domain.fire.domain.service.FireSaveService;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FireThrowUseCase {

    public final FireSaveService fireSaveService;
    public final FireQueryService fireQueryService;

    public FireThrowRes createFireThrow(Long throwMemberId, Long receiveMemberId) {



        return FireMapper.mapToFireThrowRes(fireSaveService.save(Fire.builder()
                .throwMemberId(throwMemberId)
                .receiveMemberId(receiveMemberId)
                .build()));
    }
}
