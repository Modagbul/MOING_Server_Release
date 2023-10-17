package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.response.DeleteTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.team.exception.NotAuthByTeamException;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
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
    private final TeamMemberGetService teamMemberGetService;

    public DeleteTeamResponse disbandTeam(String socialId, Long teamId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);

        if (checkLeaderUserCase.isTeamLeader(member, team)) {
            //TODO: 팀의 모든 멤버 isDeleted true 해주환
            team.deleteTeam();
        } else {
            throw new NotAuthByTeamException();
        }
        return new DeleteTeamResponse(team.getTeamId());
    }
}
