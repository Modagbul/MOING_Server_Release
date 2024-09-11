package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.response.DeleteTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DisbandTeamUseCase {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final CheckLeaderUseCase checkLeaderUseCase;
    private final TeamMemberGetService teamMemberGetService;

    public DeleteTeamResponse disbandTeam(String socialId, Long teamId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);
        checkLeaderUseCase.validateTeamLeader(member, team);
        team.deleteTeam();
        if (team.getNumOfMember() == 1) { // 1명인 경우 3일 유예기간 없음
            TeamMember teamMember = teamMemberGetService.getTeamMemberNotDeleted(member, team);
            teamMember.deleteMember(team);
        }
        return new DeleteTeamResponse(teamId);
    }
}
