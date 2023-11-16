package com.moing.backend.domain.mission.application.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RepeatMissionBoardRes {
    private Long missionId;
    private String title;

    private String dueTo; // 요일 상태 넘겨주기
//    private String status;
    private Long done;
    private int number;
    private String way;
    private String status;


    public RepeatMissionBoardRes(Long missionId, String title, Long done,int number,String way,String status) {
        this.missionId = missionId;
        this.title = title;
        this.dueTo="False";
        this.number = number;
        this.done = done;
        this.way = way;
        this.status = status;
    }

    public RepeatMissionBoardRes(Long missionId, String title, String dueTo, Long done, int number,String way) {
        this.missionId = missionId;
        this.title = title;
        this.dueTo = "False";
        this.done = done;
        this.number = number;
        this.way = way;
    }

    @Builder
    public RepeatMissionBoardRes(Long missionId, String title, String dueTo, Long done, int number, String way, String status) {
        this.missionId = missionId;
        this.title = title;
        this.dueTo = dueTo;
        this.done = done;
        this.number = number;
        this.way = way;
        this.status = status;
    }
}
