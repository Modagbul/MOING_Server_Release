package com.moing.backend.domain.missionArchive.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveHeartRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.global.annotation.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public class MissionArchiveMapper {

    public static MissionArchive mapToMissionArchive(MissionArchiveReq missionArchiveReq, Member member, Mission mission) {
        return MissionArchive.builder()
                .archive(missionArchiveReq.getArchive())
                .status(MissionArchiveStatus.valueOf(missionArchiveReq.getStatus()))
                .member(member)
                .mission(mission)
                .build();
    }

    public static MissionArchiveRes mapToMissionArchiveRes(MissionArchive missionArchive) {
        return MissionArchiveRes.builder()
                .archiveId(missionArchive.getId())
                .archive(missionArchive.getArchive())
                .createdDate(missionArchive.getCreatedDate().toString())
                .hearts(missionArchive.getHearts())
                .build();
    }


    public static PersonalArchive mapToPersonalArchive(MissionArchive missionArchive) {
        Member member = missionArchive.getMember();
        return PersonalArchive.builder()
                .archiveId(missionArchive.getId())
                .nickname(member.getNickName())
                .profileImg(member.getProfileImage())
                .archive(missionArchive.getArchive())
                .createdDate(missionArchive.getCreatedDate().toString())
                .hearts(missionArchive.getHearts())
                .build();
    }

    public static List<PersonalArchive> mapToPersonalArchiveList(List<MissionArchive> missionArchiveList) {
        List<PersonalArchive> personalArchiveList = new ArrayList<>();
        missionArchiveList.forEach(
                missionArchive -> personalArchiveList.add(MissionArchiveMapper.mapToPersonalArchive(missionArchive))
        );
        return personalArchiveList;
    }


    public static SingleMissionBoardRes mapToSingleMissionBoardRes(MissionArchive missionArchive) {
        Member member = missionArchive.getMember();
        Mission mission = missionArchive.getMission();
        return SingleMissionBoardRes.builder()
                .missionId(mission.getId())
                .title(mission.getTitle())
                .missionType(mission.getType().name())
                .dueTo(mission.getDueTo().toString())
                .status(missionArchive.getStatus().name())
                .build();

    }

    public static List<SingleMissionBoardRes> mapToSingleMissionBoardResList( List<MissionArchive> missionArchives) {
        List<SingleMissionBoardRes> singleMissionBoardResList = new ArrayList<>();
        missionArchives.forEach(
                missionArchive -> singleMissionBoardResList.add(MissionArchiveMapper.mapToSingleMissionBoardRes(missionArchive))
        );

        return singleMissionBoardResList;
    }

    public static MissionArchiveHeartRes mapToMissionArchiveHeartRes(MissionArchive missionArchive,Boolean heartStatus) {
        return MissionArchiveHeartRes.builder()
                .archiveId(missionArchive.getId())
                .hearts(missionArchive.getHearts())
                .heartStatus(heartStatus.toString())
                .build();
    }



}
