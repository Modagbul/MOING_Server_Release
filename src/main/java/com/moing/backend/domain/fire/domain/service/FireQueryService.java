package com.moing.backend.domain.fire.domain.service;

import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.repository.FireCustomRepository;
import com.moing.backend.domain.fire.domain.repository.FireRepository;
import com.moing.backend.domain.fire.exception.NotFoundFireException;
import com.moing.backend.domain.fire.exception.NotFoundFireReceiversException;
import com.moing.backend.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FireQueryService {

    private final FireRepository fireRepository;

    public boolean hasFireCreatedWithinOneHour(Long throwMemberId, Long receiveMemberId) {
        return fireRepository.hasFireCreatedWithinOneHour(throwMemberId,receiveMemberId);
    }

    public List<Member> getNotYetMissionMember(Long teamId, Long missionId) {
        return fireRepository.getFireReceivers(teamId, missionId).orElseThrow(NotFoundFireReceiversException::new);
    }

}
