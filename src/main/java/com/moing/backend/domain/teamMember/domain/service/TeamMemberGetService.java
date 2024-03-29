package com.moing.backend.domain.teamMember.domain.service;

import com.moing.backend.domain.history.application.dto.response.NewUploadInfo;
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

    public TeamMember getTeamMember(Member member, Team team){
        return teamMemberRepository.findTeamMemberByTeamAndMember(team, member).orElseThrow(NotFoundByTeamIdException::new);
    }

    public TeamMember getTeamMemberNotDeleted(Member member, Team team) {
        return teamMemberRepository.findTeamMemberByTeamAndMember(team, member)
                .filter(teamMember -> !teamMember.isDeleted())
                .orElseThrow(NotFoundByTeamIdException::new);
    }


    public List<TeamMemberInfo> getTeamMemberInfo(Long memberId, Long teamId){
        return teamMemberRepository.findTeamMemberInfoByTeamId(memberId, teamId);
    }

    public Optional<List<NewUploadInfo>> getNewUploadInfo(Long teamId, Long memberId) {
        return teamMemberRepository.findNewUploadInfo(teamId, memberId);
    }


}
