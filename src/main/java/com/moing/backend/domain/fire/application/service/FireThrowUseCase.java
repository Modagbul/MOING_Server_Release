package com.moing.backend.domain.fire.application.service;

import com.moing.backend.domain.fire.application.dto.req.FireThrowReq;
import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.application.mapper.FireMapper;
import com.moing.backend.domain.fire.domain.entity.Fire;
import com.moing.backend.domain.fire.domain.service.FireQueryService;
import com.moing.backend.domain.fire.domain.service.FireSaveService;
import com.moing.backend.domain.fire.exception.NoAuthThrowFireException;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FireThrowUseCase {

    public final FireSaveService fireSaveService;
    public final FireQueryService fireQueryService;
    private final MemberGetService memberGetService;
    private final FireThrowAlarmUseCase fireThrowAlarmUseCase;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MissionQueryService missionQueryService;
    private final TeamGetService teamGetService;

    public FireThrowRes createFireThrow(String userId, Long receiveMemberId, Long missionId, Long teamId, FireThrowReq fireThrowReq) {

        Member throwMember = memberGetService.getMemberBySocialId(userId);
        Member receiveMember = memberGetService.getMemberByMemberId(receiveMemberId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        Mission mission = missionQueryService.findMissionById(missionId);

        // 나에게 던질 수 없음
        if (throwMember.equals(receiveMember)) {
            throw new NoAuthThrowFireException();
        }

        // 1시간전 불 던진 기록이 있다면, 던질 수 없음
        if (!fireQueryService.hasFireCreatedWithinOneHour(throwMember.getMemberId(), receiveMemberId)) {
            throw new NoAuthThrowFireException();
        }

        fireThrowAlarmUseCase.sendFireThrowAlarm(throwMember, receiveMember, team, mission, fireThrowReq);

        Fire save = fireSaveService.save(Fire.builder()
                .throwMemberId(throwMember.getMemberId())
                .receiveMemberId(receiveMemberId)
                .build());

        return FireMapper.mapToFireThrowRes(fireSaveService.save(save));
    }

    public List<FireReceiveRes> getFireReceiveList(String userId,Long teamId, Long missionId) {
        Member member = memberGetService.getMemberBySocialId(userId);
        Long memberId = member.getMemberId();

        List<FireReceiveRes> fireReceiveRes = fireQueryService.getNotYetMissionMember(teamId, missionId,memberId);
        fireReceiveRes.forEach(
                res -> res.updateFireStatus(fireQueryService.hasFireCreatedWithinOneHour(memberId,res.getReceiveMemberId())
        ));

//        if (!missionArchiveQueryService.isDone(memberId, missionId)) {
//            fireReceiveRes.add(0,FireReceiveRes.builder()
//                    .receiveMemberId(memberId)
//                    .nickname(member.getNickName())
//                    .fireStatus("False")
//                    .build());
//        }

        return fireReceiveRes;
    }



}
