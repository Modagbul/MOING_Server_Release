package com.moing.backend.domain.missionArchive.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.annotation.Mapper;

import java.util.ArrayList;
import java.util.List;

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
        return MissionArchiveRes.builder()
                .archive(missionArchive.getArchive())
                .createdDate(missionArchive.getCreatedDate().toString())
                .build();
    }


    public static PersonalArchive mapToPersonalArchive(MissionArchive missionArchive) {
        Member member = missionArchive.getMember();
        return PersonalArchive.builder()
                .nickname(member.getNickName())
                .profileImg(member.getProfileImage())
                .archive(missionArchive.getArchive())
                .createdDate(missionArchive.getCreatedDate().toString())
                .build();
    }



}
