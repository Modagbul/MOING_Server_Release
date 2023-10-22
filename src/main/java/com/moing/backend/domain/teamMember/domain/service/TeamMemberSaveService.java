package com.moing.backend.domain.teamMember.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.exception.AlreadyJoinTeamException;
import com.moing.backend.domain.team.exception.AlreadyWithdrawTeamException;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class TeamMemberSaveService {
    private final TeamMemberRepository teamMemberRepository;
    public void addTeamMember(Team team, Member member){
        Optional<TeamMember> teamMember = teamMemberRepository.findTeamMemberByTeamAndMember(team, member);
        if (teamMember.isPresent()) {
            if (teamMember.get().isDeleted()) {
                throw new AlreadyWithdrawTeamException();
            } else {
                throw new AlreadyJoinTeamException();
            }
        } else {
            TeamMember newMember = new TeamMember();
            newMember.updateMember(member);
            newMember.updateTeam(team);
            team.addTeamMember();
            this.teamMemberRepository.save(newMember);
        }
    }
}
