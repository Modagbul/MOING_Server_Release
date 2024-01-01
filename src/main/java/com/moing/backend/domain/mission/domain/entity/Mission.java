package com.moing.backend.domain.mission.domain.entity;

import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionState.domain.entity.MissionState;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Mission extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    private String title;
    private LocalDateTime dueTo;
    private String rule;

    @Column(nullable = false, columnDefinition="TEXT", length = 300)
    private String content;

    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(value = EnumType.STRING)
    private MissionType type;

    @Enumerated(value = EnumType.STRING)
    private MissionStatus status;

    @Enumerated(value = EnumType.STRING)
    private MissionWay way;

    @OneToMany(mappedBy = "mission")
    List<MissionArchive> missionArchiveList = new ArrayList<>();
    
    @OneToMany(mappedBy = "mission")
    List<MissionState> missionStateList = new ArrayList<>();

    @Builder
    public Mission(String title, LocalDateTime dueTo, String rule, String content, int number, Team team, MissionType type, MissionStatus status, MissionWay way) {
        this.title = title;
        this.dueTo = dueTo;
        this.rule = rule;
        this.content = content;
        this.number = number;
        this.team = team;
        this.type = type;
        this.status = status;
        this.way = way;
    }


    public void updateMission(MissionReq missionReq) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        this.title = missionReq.getTitle();
        this.dueTo = LocalDateTime.parse(missionReq.getDueTo(), formatter);
        this.rule = missionReq.getRule();
        this.content = missionReq.getContent();
        this.number = missionReq.getNumber();
        this.type = MissionType.valueOf(missionReq.getType());
        this.way = MissionWay.valueOf(missionReq.getWay());

    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void updateStatus(MissionStatus missionStatus) {
        this.status = missionStatus;
    }

    public void updateDueTo(LocalDateTime dueTo) {
        this.dueTo = dueTo;
    }
}
