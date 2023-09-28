package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveHeartReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveHeartRes;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveDeleteService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveHeartUseCase {


    private final MissionArchiveSaveService missionArchiveSaveService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MissionArchiveDeleteService missionArchiveDeleteService;

    private final MissionQueryService missionQueryService;

    private final MemberGetService memberGetService;

    private final MissionArchiveScoreService missionArchiveScoreService;

    public MissionArchiveHeartRes pushHeart(MissionArchiveHeartReq missionArchiveHeartReq) {

        MissionArchive missionArchive = missionArchiveQueryService.findByMissionArchiveId(missionArchiveHeartReq.getArchiveId());

        Boolean heartStatus = Boolean.valueOf(missionArchiveHeartReq.getHeartStatus());
        missionArchive.updateHearts(heartStatus);
        return MissionArchiveMapper.mapToMissionArchiveHeartRes(missionArchive, heartStatus);

    }

}
