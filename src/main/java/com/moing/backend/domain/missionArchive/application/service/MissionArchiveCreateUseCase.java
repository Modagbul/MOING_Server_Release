package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.mission.domain.service.MissionDeleteService;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchive;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveDeleteService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveSaveService;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper.mapToPersonalArchive;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveCreateUseCase {

    private final MissionArchiveSaveService missionArchiveSaveService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MissionArchiveDeleteService missionArchiveDeleteService;

    private final MissionQueryService missionQueryService;

    private final MemberGetService memberGetService;

    // 미션 인증 및 재인증 (수정하기도 포함됨)
    public MissionArchiveRes createArchive(String userSocialId, Long missionId, MissionArchiveReq missionReq) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();


        // 사진 제출 했다면,
        if (mission.getWay() == MissionWay.PHOTO && missionArchiveQueryService.isDone(member.getMemberId(), missionId)) {

            //s3삭제
            //archive set presignedURL

            String url = null;
            missionReq.setArchive(url);

            return saveArchive(mission, missionReq,member);

        }

        return saveArchive(mission, missionReq,member);

    }


    public  MissionArchiveRes saveArchive(Mission mission,MissionArchiveReq missionReq,Member member) {
        MissionArchive save = missionArchiveSaveService.save(MissionArchiveMapper.mapToMissionArchive(missionReq, member, mission));

        // 미션 완료 처리
        if (mission.getMissionArchiveList().size() == 3-1) {
            mission.setStatus(MissionStatus.DONE);
        }

        return MissionArchiveMapper.mapToMissionArchiveRes(save);

    }



}
