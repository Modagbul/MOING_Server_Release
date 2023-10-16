package com.moing.backend.domain.missionHeart.application.mapper;

import com.moing.backend.domain.missionHeart.application.dto.MissionHeartRes;
import com.moing.backend.domain.missionHeart.domain.constant.MissionHeartStatus;
import com.moing.backend.domain.missionHeart.domain.entity.MissionHeart;
import com.moing.backend.global.annotation.Mapper;

@Mapper
public class MissionHeartMapper {

    public static MissionHeart mapToMissionHeart(Long memberId, Long archiveId, MissionHeartStatus missionHeartStatus) {
        return MissionHeart.builder()
                .pushMemberId(memberId)
                .missionArchiveId(archiveId)
                .heartStatus(missionHeartStatus)
                .build();
    }

    public static MissionHeartRes mapToMissionHeartRes(MissionHeart missionHeart) {
        return MissionHeartRes.builder()
                .missionHeartStatus(missionHeart.getHeartStatus().name())
                .build();
    }
}
