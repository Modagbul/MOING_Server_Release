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
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveSaveService;
import com.moing.backend.domain.missionArchive.exception.NoMoreMissionArchiveException;
import com.moing.backend.domain.missionState.application.service.MissionStateUseCase;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamScore.application.service.TeamScoreUpdateUseCase;
import com.moing.backend.domain.teamScore.domain.entity.ScoreStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveCreateUseCase {

    private final MissionArchiveSaveService missionArchiveSaveService;
    private final MissionArchiveQueryService missionArchiveQueryService;

    private final MissionQueryService missionQueryService;
    private final MemberGetService memberGetService;

    private final MissionStateUseCase missionStateUseCase;

    private final TeamScoreUpdateUseCase teamScoreUpdateUseCase;

    public MissionArchiveRes createArchive(String userSocialId, Long missionId, MissionArchiveReq missionReq) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Long memberId = member.getMemberId();

        Mission mission = missionQueryService.findMissionById(missionId);
        MissionArchive newArchive = MissionArchiveMapper.mapToMissionArchive(missionReq, member, mission);

        // 인증 완료한 미션인지 확인
        if (isDoneMission(memberId,mission)) {
            throw new NoMoreMissionArchiveException();
        }

        MissionArchiveRes missionArchiveRes;

        // 반복 미션일 경우
        if (mission.getType() == MissionType.REPEAT) {

            // 당일 1회 인증만 가능
            if (missionArchiveQueryService.isAbleToArchiveToday(memberId, missionId)) {
                throw new NoMoreMissionArchiveException();
            }

            newArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId) + 1);
            missionStateUseCase.updateMissionState(member, mission, newArchive);

            missionArchiveRes = MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveSaveService.save(newArchive), memberId);

        }

        // 한번 미션일 경우
        else {

            // 미션 생성 후 처음 미션 인증 시도 시 ongoing 으로 변경 -> 읽음처리 구현되면 로직 삭제
            if(mission.getStatus() == MissionStatus.WAIT) {
                mission.updateStatus(MissionStatus.ONGOING);
            }

            newArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId)+1);
            missionStateUseCase.updateMissionState(member, mission, newArchive);

            missionArchiveRes = MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveSaveService.save(newArchive), memberId);

            // 인증 후 n/n명 인증 성공 리턴값 업데이트
            Long doneSingleArchives = missionArchiveQueryService.findDoneSingleArchives(missionId);
            missionArchiveRes.updateCount(doneSingleArchives);

        }
        // TODO : 소모임원 3명 이상일 경우 보너스 점수
        if (mission.getTeam().getNumOfMember() > 2) {
            gainBonusScore(mission, newArchive);
        }
        // TODO : 미션 인증 1회당 점수
        teamScoreUpdateUseCase.gainScoreOfArchive(mission, ScoreStatus.PLUS);

        return missionArchiveRes;
    }

    // 이 미션을 완료 했는지
    private Boolean isDoneMission(Long memberId,Mission mission) {
        return missionArchiveQueryService.findMyDoneArchives(memberId, mission.getId()) >= mission.getNumber();
    }

    private void gainBonusScore(Mission mission, MissionArchive missionArchive) {

        if (mission.getType() == MissionType.ONCE) {

            if (isAbleToFinishOnceMission(mission)) {
                mission.updateStatus(MissionStatus.SUCCESS);
                teamScoreUpdateUseCase.gainScoreOfBonus(mission);
            }

        } else {
            if (isAbleToFinishRepeatMission(mission, missionArchive)) {
                teamScoreUpdateUseCase.gainScoreOfBonus(mission);

            }

        }
    }

    private boolean isAbleToFinishRepeatMission(Mission mission, MissionArchive archive) {
        return mission.getNumber() <= archive.getCount();
    }

    private boolean isAbleToFinishOnceMission(Mission mission) {

        Team team = mission.getTeam();

        Integer total = team.getNumOfMember();
        Long done = missionArchiveQueryService.stateCountByMissionId(mission.getId());

        return done >= total;

    }


}
