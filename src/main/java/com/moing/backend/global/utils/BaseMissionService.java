package com.moing.backend.global.utils;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.response.BaseMissionServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BaseMissionService {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final TeamMemberGetService teamMemberGetService;

        public BaseMissionServiceResponse getCommonData(String socialId, Long teamId, Long missionArchiveId){
            Member member = memberGetService.getMemberBySocialId(socialId);
            Team team = teamGetService.getTeamByTeamId(teamId);
            MissionArchive missionArchive=missionArchiveQueryService.findByMissionArchiveId(missionArchiveId);
            TeamMember teamMember = teamMemberGetService.getTeamMember(member, team);

            return new BaseMissionServiceResponse(member, team, missionArchive, teamMember);
    }
}
