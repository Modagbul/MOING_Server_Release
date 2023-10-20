package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.infra.image.application.service.IssuePresignedUrlUseCase;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveDeleteService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveSaveService;
import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.domain.missionHeart.domain.service.MissionHeartQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveUpdateUseCase {

    private final MissionArchiveSaveService missionArchiveSaveService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MissionArchiveDeleteService missionArchiveDeleteService;
    private final MissionHeartQueryService missionHeartQueryService;

    private final MissionQueryService missionQueryService;

    private final MemberGetService memberGetService;

    private final IssuePresignedUrlUseCase getPresignedUrlUseCase;


    // 미션 재인증 (수정하기도 포함됨)
    public MissionArchiveRes updateArchive(String userSocialId, Long missionId, MissionArchiveReq missionReq) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Long memberId = member.getMemberId();

        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        // 사진 제출 했다면,
        if (mission.getWay() == MissionWay.PHOTO && missionArchiveQueryService.isDone(memberId, missionId)) {
            //s3삭제

        }

        MissionArchive updateArchive = missionArchiveQueryService.findMyArchive(memberId, missionId).get(0);

        if (mission.getType().equals(MissionType.ONCE)) {
            // missionArchive 2개 이상일 때 예외처리 필요
//            if (isDoneSingleMission(mission)) {
//                missionArchiveScoreService.addScore(team);
//            }
            updateArchive.updateCount(1L);
        }
        else{
            updateArchive.updateCount(missionArchiveQueryService.findMyDoneArchives(memberId, missionId)+1);
        }

        updateArchive.updateArchive(missionReq);

        return MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveSaveService.save(updateArchive),memberId);


    }


}
