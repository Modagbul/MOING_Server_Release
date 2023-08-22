package com.moing.backend.domain.mission.domain.entity;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String content;
    private int number;

    @Enumerated(value = EnumType.STRING)
    private MissionType type;

    @Enumerated(value = EnumType.STRING)
    private MissionStatus status;

    @Enumerated(value = EnumType.STRING)
    private MissionWay way;

    @OneToMany(mappedBy = "mission")
    List<MissionArchive> missionArchiveList = new ArrayList<>();

    @Builder
    public Mission(String title, LocalDateTime dueTo, String rule, String content, int number, MissionType type, MissionStatus status, MissionWay way) {
        this.title = title;
        this.dueTo = dueTo;
        this.rule = rule;
        this.content = content;
        this.number = number;
        this.type = type;
        this.status = status;
        this.way = way;
    }
}
