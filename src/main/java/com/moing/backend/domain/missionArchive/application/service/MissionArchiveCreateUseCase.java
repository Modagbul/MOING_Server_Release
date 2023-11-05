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
import com.moing.backend.domain.missionState.domain.service.MissionStateSaveService;
import com.moing.backend.domain.missionHeart.domain.service.MissionHeartQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
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

    private final MissionHeartQueryService missionHeartQueryService;

    private final MissionQueryService missionQueryService;
    private final MemberGetService memberGetService;

    private final MissionStateSaveService missionStateSaveService;
    private final MissionStateUseCase missionStateUseCase;

    private final TeamScoreLogicUseCase teamScoreLogicUseCase;

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
        // 반복 미션일 경우
        if (mission.getType() == MissionType.REPEAT) {
            // 당일 1회 인증만 가능
            if(!missionArchiveQueryService.findDoneTodayArchive(memberId,missionId))
                newArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId)+1);
            else
                throw new NoMoreMissionArchiveException();

        }else {
            newArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId)+1);
        }

        missionStateUseCase.updateMissionState(member, mission, newArchive);
        return MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveSaveService.save(newArchive),memberId);

    }

    // 이 미션을 완료 했는지
    public Boolean isDoneMission(Long memberId,Mission mission) {
        return missionArchiveQueryService.findMyDoneArchives(memberId, mission.getId()) >= mission.getNumber();
    }





}
