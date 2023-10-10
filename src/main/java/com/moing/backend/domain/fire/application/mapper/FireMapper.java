package com.moing.backend.domain.fire.application.mapper;

import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.domain.entity.Fire;

public class FireMapper {

    public static FireThrowRes mapToFireThrowRes(Fire fire) {
        return FireThrowRes.builder()
                .receiveMemberId(fire.getReceiveMemberId())
                .build();
    }
}
