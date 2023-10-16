package com.moing.backend.domain.missionArchive.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveHeartRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchiveRes;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
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
                .status(missionArchive.getStatus().name())
                .count(missionArchive.getCount())
                .build();
    }

    public static List<MissionArchiveRes> mapToMissionArchiveResList(List<MissionArchive> missionArchiveList) {
        List<MissionArchiveRes> missionArchiveResList = new ArrayList<>();
        missionArchiveList.forEach(
                missionArchive -> missionArchiveResList.add(MissionArchiveMapper.mapToMissionArchiveRes(missionArchive))
        );
        return missionArchiveResList;
    }


    public static PersonalArchiveRes mapToPersonalArchive(MissionArchive missionArchive) {
        Member member = missionArchive.getMember();
        return PersonalArchiveRes.builder()
                .archiveId(missionArchive.getId())
                .nickname(member.getNickName())
                .profileImg(member.getProfileImage())
                .archive(missionArchive.getArchive())
                .createdDate(missionArchive.getCreatedDate().toString())
                .status(missionArchive.getStatus().name())
                .count(missionArchive.getCount())
                .build();
    }

    public static List<PersonalArchiveRes> mapToPersonalArchiveList(List<MissionArchive> missionArchiveList) {
        List<PersonalArchiveRes> personalArchiveList = new ArrayList<>();
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
                .heartStatus(heartStatus.toString())
                .build();
    }


    public static List<FinishMissionBoardRes> mapToFinishMissionBoardResList(List<MissionArchive> missionArchives) {
        List<FinishMissionBoardRes> finishMissionBoardResList = new ArrayList<>();
        missionArchives.forEach(
                missionArchive -> finishMissionBoardResList.add(MissionArchiveMapper.mapToFinishMissionBoardRes(missionArchive))
        );

        return finishMissionBoardResList;
    }

    public static FinishMissionBoardRes mapToFinishMissionBoardRes(MissionArchive missionArchive) {
        Mission mission = missionArchive.getMission();
        return FinishMissionBoardRes.builder()
                .missionId(mission.getId())
                .missionType(mission.getType().name())
                .title(mission.getTitle())
                .dueTo(mission.getDueTo().toString())
                .status(missionArchive.getStatus().name())
                .build();
    }



}
