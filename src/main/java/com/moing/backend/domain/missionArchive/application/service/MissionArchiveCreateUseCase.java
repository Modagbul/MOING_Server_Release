package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.infra.image.application.service.IssuePresignedUrlUseCase;
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

    private final MissionArchiveScoreService missionArchiveScoreService;

    // 미션 인증
    public MissionArchiveRes createArchive(String userSocialId, Long missionId, MissionArchiveReq missionReq) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        // missionArchive 2개 이상일 때 예외처리 필요
        MissionArchive missionArchive = missionArchiveSaveService.save(MissionArchiveMapper.mapToMissionArchive(missionReq, member, mission));

//        if (isDoneSingleMission(mission)) {
//            missionArchiveScoreService.addScore(team);
//        }

        return MissionArchiveMapper.mapToMissionArchiveRes(missionArchive);

    }

    // 모든 모임원이 단일 미션을 완료하였는지
    public Boolean isDoneSingleMission(Mission mission) {
        Team team = mission.getTeam();
        if (mission.getMissionArchiveList().size() == team.getLeaderId()-1) {
            mission.setStatus(MissionStatus.END);
            return true;
        }
        return false;
    }



}
