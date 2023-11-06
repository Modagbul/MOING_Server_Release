package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.res.GatherRepeatMissionRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchivePhotoRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MyTeamsRes;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionGatherBoardUseCase {

    private final MissionQueryService missionQueryService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;

    public List<GatherSingleMissionRes> getAllActiveSingleMissions(String userId) {
        Long memberId  = memberGetService.getMemberBySocialId(userId).getMemberId();
        return missionQueryService.findAllSingleMission(memberId);

    }
    public List<GatherRepeatMissionRes> getAllActiveRepeatMissions(String userId) {
        Long memberId  = memberGetService.getMemberBySocialId(userId).getMemberId();
        return missionQueryService.findAllRepeatMission(memberId);

    }

    public List<MissionArchivePhotoRes> getArchivePhotoByTeamRes(String userId) {
        Long memberId  = memberGetService.getMemberBySocialId(userId).getMemberId();

        List<Long> teamIdByMemberId = teamGetService.getTeamIdByMemberId(memberId);

        return missionArchiveQueryService.findTop5ArchivesByTeam(teamIdByMemberId);
    }


    public List<MyTeamsRes> getMyTeams(String userId) {
        Long memberId  = memberGetService.getMemberBySocialId(userId).getMemberId();
        List<Long> teamIdByMemberId = teamGetService.getTeamIdByMemberId(memberId);

        return teamGetService.getTeamNameByTeamId(teamIdByMemberId);

    }




}
