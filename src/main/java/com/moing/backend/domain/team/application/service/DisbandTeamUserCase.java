package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.team.application.dto.response.DeleteTeamResponse;
import com.moing.backend.domain.team.application.mapper.TeamMapper;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.team.exception.NotAuthByTeamException;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberDeleteService;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DisbandTeamUserCase {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final CheckLeaderUserCase checkLeaderUserCase;
    private final TeamMemberDeleteService teamMemberDeleteService;
    private final MissionQueryService missionQueryService;
    private final TeamMapper teamMapper;

    public DeleteTeamResponse disbandTeam(String socialId, Long teamId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);

        if (checkLeaderUserCase.isTeamLeader(member, team)) {
            teamMemberDeleteService.deleteAllTeamMembers(team.getTeamId());
            team.deleteTeam();
        } else {
            throw new NotAuthByTeamException();
        }
        return teamMapper.toDeleteTeamResponse(missionQueryService.findMissionsCountByTeam(team.getTeamId()), team);
    }
}
