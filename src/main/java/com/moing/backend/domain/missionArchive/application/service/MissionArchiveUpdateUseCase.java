package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.infra.image.application.service.IssuePresignedUrlUseCase;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveDeleteService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveSaveService;
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

    private final MissionQueryService missionQueryService;

    private final MemberGetService memberGetService;

    private final IssuePresignedUrlUseCase getPresignedUrlUseCase;


    // 미션 재인증 (수정하기도 포함됨)
    public MissionArchiveRes updateArchive(String userSocialId, Long missionId, MissionArchiveReq missionReq) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        // 사진 제출 했다면,
        if (mission.getWay() == MissionWay.PHOTO && missionArchiveQueryService.isDone(member.getMemberId(), missionId)) {
            //s3삭제

        }

        MissionArchive missionArchive = missionArchiveQueryService.findMyArchive(member.getMemberId(), missionId).get(0);

        missionArchive.updateArchive(missionReq);

        return MissionArchiveMapper.mapToMissionArchiveRes(missionArchive);
    }


}
