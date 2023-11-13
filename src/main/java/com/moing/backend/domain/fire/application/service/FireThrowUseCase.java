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

    private final FcmService fcmService;

    public FireThrowRes createFireThrow(String userId, Long receiveMemberId) {

        Member throwMember = memberGetService.getMemberBySocialId(userId);
        Member receiveMember = memberGetService.getMemberByMemberId(receiveMemberId);

        if (!fireQueryService.hasFireCreatedWithinOneHour(throwMember.getMemberId(), receiveMemberId)) {
            throw new NoAuthThrowFireException();
        }

        String title = "어라… 왜 이렇게 발등이 뜨겁지?🤨";

        String message = getMessage(throwMember.getNickName(), receiveMember.getNickName(), (int) random() * 2);
        SingleRequest singleRequest = new SingleRequest(receiveMember.getFcmToken(), title, message);

        fcmService.sendSingleDevice(singleRequest);

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

    public String getMessage(String pusher, String receiver, int num) {

        switch (num) {
            case 0: return pusher + "님이" + receiver + "님에게 불을 던졌어요! 어서 미션을 인증해볼까요?";
            case 1: return receiver + "님! " + pusher + "님이 던진 불에 타버릴지도 몰라요! 어서 인증하러갈까요?";
        }
        return pusher + "님이" + receiver + "님에게 불을 던졌어요! 어서 미션을 인증해볼까요?";
    }


}
