package com.moing.backend.domain.mission.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.Team;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.global.annotation.Mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public class MissionMapper {

    public static Mission mapToMission(MissionReq missionReq, Team team, Member member, MissionType type) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return Mission.builder()
                .title(missionReq.getTitle())
                .dueTo(LocalDateTime.parse(missionReq.getDueTo(), formatter))
                .rule(missionReq.getRule())
                .content(missionReq.getContent())
                .way(MissionWay.valueOf(missionReq.getWay()))
                .type(MissionType.valueOf(missionReq.getType()))
                .number(missionReq.getNumber())
                .status(MissionStatus.WAIT)
                .build();

    }

    public static MissionCreateRes mapToMissionCreateRes(Mission mission) {


        return MissionCreateRes.builder()
                .title(mission.getTitle())
                .dueTo(mission.getDueTo().toString())
                .rule(mission.getRule())
                .way(mission.getWay().name())
                .content(mission.getContent())
                .type(mission.getType().name())
                .status(mission.getStatus().name())
                .build();
    }

    public static MissionReadRes mapToMissionReadRes(Mission mission) {

        return MissionReadRes.builder()
                .title(mission.getTitle())
                .dueTo(mission.getDueTo().toString())
                .rule(mission.getRule())
                .way(mission.getWay().name())
                .content(mission.getContent())
                .type(mission.getType().name())
                .build();
    }
}
