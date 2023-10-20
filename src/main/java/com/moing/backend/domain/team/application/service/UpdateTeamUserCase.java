package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.request.UpdateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.UpdateTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.team.exception.NotAuthByTeamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateTeamUserCase {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final CheckLeaderUserCase checkLeaderUserCase;

    public UpdateTeamResponse updateTeam(UpdateTeamRequest updateTeamRequest, String socialId, Long teamId){
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team=teamGetService.getTeamByTeamId(teamId);
        if (checkLeaderUserCase.isTeamLeader(member, team)) {
            team.updateTeam(updateTeamRequest.getName(), updateTeamRequest.getIntroduction(), updateTeamRequest.getProfileImgUrl());
        } else {
            throw new NotAuthByTeamException();
        }
        return new UpdateTeamResponse(team.getTeamId());
    }
}
