package com.moing.backend.domain.mission.application.dto.res;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
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
    private String dueTo; // 날짜
    private String title;
    private String status;
    private String missionType;
    private String missionWay;

    public FinishMissionBoardRes(Long missionId, String dueTo, String title, String status, String missionType,String missionWay) {
        this.missionId = missionId;
        this.dueTo = dueTo;
        this.title = title;
        this.status = status;
        this.missionType = missionType;
        this.missionWay = missionWay;
    }
}
