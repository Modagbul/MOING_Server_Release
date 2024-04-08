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
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveDeleteService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveSaveService;
import com.moing.backend.domain.missionArchive.exception.NoMoreMissionArchiveException;
import com.moing.backend.domain.missionArchive.exception.NotYetMissionArchiveException;
import com.moing.backend.domain.missionState.application.service.MissionStateUseCase;
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
    private final MissionQueryService missionQueryService;

    private final MemberGetService memberGetService;
    private final MissionStateUseCase missionStateUseCase;


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

        MissionArchiveRes missionArchiveRes;

        // 반복 미션일 경우
        if (mission.getType() == MissionType.REPEAT) {
            // 아직 열리지 않은 반복미션 접근 제한
            if (mission.getStatus() == MissionStatus.WAIT) {
                throw new NotYetMissionArchiveException();
            }

            // 당일 1회 인증만 가능
            if (missionArchiveQueryService.isAbleToArchiveToday(memberId, missionId)) {
                throw new NoMoreMissionArchiveException();
            } else {
                newArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId) + 1);
            }

            missionStateUseCase.updateMissionState(member, mission, newArchive);
            missionArchiveRes = MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveSaveService.save(newArchive), memberId);

        }

        // 한번 미션일 경우
        else {

            // 미션 생성 후 처음 미션 인증 시도 시 ongoing 으로 변경
            if(mission.getStatus() == MissionStatus.WAIT) {
                mission.updateStatus(MissionStatus.ONGOING);
            }

            newArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId)+1);

            missionStateUseCase.updateMissionState(member, mission, newArchive);
            missionArchiveRes = MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveSaveService.save(newArchive), memberId);

            // 인증 후 n/n명 인증 성공 리턴값 업데이트
            missionArchiveRes.updateCount(missionArchiveQueryService.findDoneSingleArchives(missionId));
        }
        return missionArchiveRes;

    }

    // 이 미션을 완료 했는지
    private Boolean isDoneMission(Long memberId,Mission mission) {
        return missionArchiveQueryService.findMyDoneArchives(memberId, mission.getId()) >= mission.getNumber();
    }

}
