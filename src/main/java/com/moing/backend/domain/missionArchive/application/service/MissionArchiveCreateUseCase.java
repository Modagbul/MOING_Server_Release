package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveDeleteService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveSaveService;
import com.moing.backend.domain.missionArchive.exception.NoMoreMissionArchiveException;
import com.moing.backend.domain.missionState.application.service.MissionStateUseCase;
import com.moing.backend.domain.missionState.domain.service.MissionStateQueryService;
import com.moing.backend.domain.missionState.domain.service.MissionStateSaveService;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveCreateUseCase {

    private final MissionArchiveSaveService missionArchiveSaveService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MissionArchiveDeleteService missionArchiveDeleteService;
    private final MissionQueryService missionQueryService;
    private final MemberGetService memberGetService;

    private final MissionStateSaveService missionStateSaveService;
    private final MissionStateUseCase missionStateUseCase;


    // 미션 인증
    public MissionArchiveRes createArchive(String userSocialId, Long missionId, MissionArchiveReq missionReq) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Long memberId = member.getMemberId();

        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        MissionArchive newArchive = MissionArchiveMapper.mapToMissionArchive(missionReq, member, mission);

        // 인증 완료한 미션인지 확인
        if (isDoneMission(memberId,mission)) {
            throw new NoMoreMissionArchiveException();
        }

        // 단일 미션 && 미션 종료 직전인지 확인
        if (mission.getType() == MissionType.ONCE && missionStateUseCase.isAbleToEnd(missionId)) {
            mission.updateStatus(MissionStatus.SUCCESS);
            // 점수 반영 로직

        }

        // 단일 미션
        if (mission.getType().equals(MissionType.ONCE)) {
            newArchive.updateCount(1L);
        }

        // 반복 미션
        else {
            newArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId)+1);
        }

        missionStateSaveService.saveMissionState(member,mission, MissionArchiveStatus.COMPLETE);
        return MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveSaveService.save(newArchive));

    }

    // 이 미션을 완료 했는지
    public Boolean isDoneMission(Long memberId,Mission mission) {
        return missionArchiveQueryService.findMyDoneArchives(memberId, mission.getId()) >= mission.getNumber();
    }





}
