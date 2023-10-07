package com.moing.backend.domain.mission.application.dto.res;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class FinishMissionBoardRes {
    private Long missionId;
    private LocalDateTime dueTo; // 날짜
    private String title;
    private MissionArchiveStatus status;
    private MissionType missionType;

    public FinishMissionBoardRes(Long missionId, LocalDateTime dueTo, String title, MissionArchiveStatus status, MissionType missionType) {
        this.missionId = missionId;
        this.dueTo = dueTo;
        this.title = title;
        this.status = status;
        this.missionType = missionType;
    }
}
