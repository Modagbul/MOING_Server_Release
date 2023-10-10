package com.moing.backend.domain.fire.application.service;

import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.application.mapper.FireMapper;
import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.repository.FireCustomRepository;
import com.moing.backend.domain.fire.domain.service.FireQueryService;
import com.moing.backend.domain.fire.domain.service.FireSaveService;
import com.moing.backend.domain.fire.exception.NoAuthThrowFireException;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FireThrowUseCase {

    public final FireSaveService fireSaveService;
    public final FireQueryService fireQueryService;
    private final MemberGetService memberGetService;

    public FireThrowRes createFireThrow(String userId, Long receiveMemberId) {

        Long throwMemberId = memberGetService.getMemberBySocialId(userId).getMemberId();

        if (!fireQueryService.hasFireCreatedWithinOneHour(throwMemberId, receiveMemberId)) {
            throw new NoAuthThrowFireException();
        }

        return FireMapper.mapToFireThrowRes(fireSaveService.save(Fire.builder()
                .throwMemberId(throwMemberId)
                .receiveMemberId(receiveMemberId)
                .build()));
    }

    public List<FireReceiveRes> getFireReceiveList(String userId,Long teamId, Long missionId) {
        Long memberId = memberGetService.getMemberBySocialId(userId).getMemberId();

        List<FireReceiveRes> fireReceiveRes = FireMapper.mapToFireReceiversList(fireQueryService.getNotYetMissionMember(teamId, missionId));
        fireReceiveRes.forEach(
                res -> res.updateFireStatus(fireQueryService.hasFireCreatedWithinOneHour(memberId,res.getReceiveMemberId())
        ));

        return fireReceiveRes;
    }
}
