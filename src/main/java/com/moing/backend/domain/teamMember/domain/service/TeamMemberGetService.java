package com.moing.backend.domain.teamMember.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.application.dto.response.TeamMemberInfo;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.exception.NotFoundByTeamIdException;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class TeamMemberGetService {
    private final TeamMemberRepository teamMemberRepository;

    public List<Long> getTeamMemberIds(Long teamId){
        return teamMemberRepository.findMemberIdsByTeamId(teamId);
    }

    public TeamMember getTeamMember(Member member, Team team){
        return teamMemberRepository.findTeamMemberByTeamAndMember(team, member).orElseThrow(()-> new NotFoundByTeamIdException());
    }

    public Optional<List<String>> getFcmTokensExceptMe(Long teamId, Long memberId) {
        return teamMemberRepository.findFcmTokensByTeamIdAndMemberId(teamId, memberId);
    }

    public Optional<List<String>> getFcmTokens(Long teamId) {
        return teamMemberRepository.findFcmTokensByTeamId(teamId);
    }

    public List<TeamMemberInfo> getTeamMemberInfo(Long teamId){
        return teamMemberRepository.findTeamMemberInfoByTeamId(teamId);
    }

    public List<TeamMember> getNotDeletedTeamMember(Long memberId){
        return teamMemberRepository.findTeamMemberByMemberId(memberId);
    }
}
