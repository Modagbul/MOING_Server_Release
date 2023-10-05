package com.moing.backend.domain.mission.application.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
public class RepeatMissionBoardRes {
    private Long missionId;
    private String title;

    private String dueTo; // 요일 상태 넘겨주기
//    private String status;
    private Long done;
    private int number;


    public RepeatMissionBoardRes(Long missionId, String title, Long done,int number) {
        this.missionId = missionId;
        this.title = title;
        this.dueTo="False";
        this.number = number;
        this.done = done;
    }
}
