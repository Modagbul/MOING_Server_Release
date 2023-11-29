package com.moing.backend.domain.fire.domain.repository;


import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FireCustomRepository {

    boolean hasFireCreatedWithinOneHour(Long throwMemberId, Long receiveMemberId);
    Optional<List<FireReceiveRes>> getFireReceivers(Long teamId, Long missionId, Long memberId);

    }
