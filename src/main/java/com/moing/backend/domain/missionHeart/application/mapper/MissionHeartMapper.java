package com.moing.backend.domain.missionHeart.application.mapper;

import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionHeart.application.dto.MissionHeartRes;
import com.moing.backend.domain.missionHeart.domain.constant.MissionHeartStatus;
import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.global.annotation.Mapper;

@Mapper
public class MissionHeartMapper {

    public static MissionHeart mapToMissionHeart(Long memberId, MissionArchive archiveId, MissionHeartStatus missionHeartStatus) {
        return MissionHeart.builder()
                .pushMemberId(memberId)
                .missionArchive(archiveId)
                .heartStatus(missionHeartStatus)
                .build();
    }

    public static MissionHeartRes mapToMissionHeartRes(MissionHeart missionHeart) {
        return MissionHeartRes.builder()
                .missionArchiveId(missionHeart.getMissionArchive().getId())
                .missionHeartStatus(missionHeart.getHeartStatus().name())
                .hearts((int) missionHeart.getMissionArchive().getHeartList().stream()
                        .filter(heart -> heart.getHeartStatus().equals( MissionHeartStatus.True))
                        .filter(heart -> heart.getId().equals( missionHeart.getId()))// heartStatus가 true인 요소만 필터링
                        .count())
                .build();
    }
}
