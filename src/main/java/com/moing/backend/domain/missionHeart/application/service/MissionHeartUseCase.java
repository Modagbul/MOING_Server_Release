package com.moing.backend.domain.missionHeart.application.service;

import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionHeart.application.dto.MissionHeartRes;
import com.moing.backend.domain.missionHeart.application.mapper.MissionHeartMapper;
import com.moing.backend.domain.missionHeart.domain.constant.MissionHeartStatus;
import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.domain.missionHeart.domain.service.MissionHeartQueryService;
import com.moing.backend.domain.missionHeart.domain.service.MissionHeartSaveService;
import com.moing.backend.domain.missionHeart.domain.service.MissionHeartUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionHeartUseCase {

    private final MissionHeartSaveService missionHeartSaveService;
    private final MissionHeartUpdateService missionHeartUpdateService;
    private final MissionHeartQueryService missionHeartQueryService;
    private final MemberGetService memberGetService;
    private final MissionArchiveQueryService missionArchiveQueryService;


    public MissionHeartRes pushHeart(String socialId,Long archiveId, String status) {

        Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();
        MissionArchive archive = missionArchiveQueryService.findByMissionArchiveId(archiveId);
        MissionHeart missionHeart = MissionHeartMapper.mapToMissionHeart(memberId, archive, MissionHeartStatus.valueOf(status));

        if(missionHeartQueryService.isAlreadyHeart(memberId, archiveId)) {
            return MissionHeartMapper.mapToMissionHeartRes(
                    missionHeartUpdateService.update(missionHeart));
        }
        else{
            return MissionHeartMapper.mapToMissionHeartRes(
                    missionHeartSaveService.save(missionHeart));
        }

    }
}
