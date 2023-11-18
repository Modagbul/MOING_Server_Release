package com.moing.backend.domain.fire.application.service;

import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.application.mapper.FireMapper;
import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.service.FireQueryService;
import com.moing.backend.domain.fire.domain.service.FireSaveService;
import com.moing.backend.domain.fire.exception.NoAuthThrowFireException;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.global.config.fcm.dto.request.SingleRequest;
import com.moing.backend.global.config.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Math.random;

@Service
@Transactional
@RequiredArgsConstructor
public class FireThrowUseCase {

    public final FireSaveService fireSaveService;
    public final FireQueryService fireQueryService;
    private final MemberGetService memberGetService;

    private final FireThrowAlarmUseCase fireThrowAlarmUseCase;

    public FireThrowRes createFireThrow(String userId, Long receiveMemberId) {

        Member throwMember = memberGetService.getMemberBySocialId(userId);
        Member receiveMember = memberGetService.getMemberByMemberId(receiveMemberId);

        // 나에게 던질 수 없음
        if (throwMember.equals(receiveMember)) {
            throw new NoAuthThrowFireException();
        }

        // 1시간전 불 던진 기록이 있다면, 던질 수 없음
        if (!fireQueryService.hasFireCreatedWithinOneHour(throwMember.getMemberId(), receiveMemberId)) {
            throw new NoAuthThrowFireException();
        }

        fireThrowAlarmUseCase.sendFireThrowAlarm(throwMember, receiveMember);

        return FireMapper.mapToFireThrowRes(fireSaveService.save(Fire.builder()
                .throwMemberId(throwMember.getMemberId())
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
