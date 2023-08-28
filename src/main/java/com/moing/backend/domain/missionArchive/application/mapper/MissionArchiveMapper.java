package com.moing.backend.domain.missionArchive.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.annotation.Mapper;

@Mapper
public class MissionArchiveMapper {

    public static MissionArchive mapToMissionArchive(MissionArchiveReq missionArchiveReq, Member member, Mission mission) {
        return MissionArchive.builder()
                .archive(missionArchiveReq.getArchive())
                .status(MissionStatus.valueOf(missionArchiveReq.getStatus()))
                .member(member)
                .mission(mission)
                .build();
    }

    public static MissionArchiveRes mapToMissionArchiveRes(MissionArchive missionArchive) {
        Mission mission = missionArchive.getMission();
        Team team = mission.getTeam();

        return MissionArchiveRes.builder()
                .title(mission.getTitle())
                .status(mission.getStatus().name())
                .dueTo(mission.getDueTo().toString())
                .totalPerson(10)
                .donePerson(mission.getMissionArchiveList().size())
                .rule(mission.getRule())
                .content(mission.getContent())
                .archive(missionArchive.getArchive())
                .createdDate(missionArchive.getCreatedDate().toString())
                .build();

    }



}
