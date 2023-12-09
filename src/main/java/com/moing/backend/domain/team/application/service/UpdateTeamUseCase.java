package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.request.UpdateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.UpdateTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.global.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateTeamUseCase {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final CheckLeaderUseCase checkLeaderUseCase;
    private final UpdateUtils updateUtils;

    @Transactional
    public UpdateTeamResponse updateTeam(UpdateTeamRequest updateTeamRequest, String socialId, Long teamId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = teamGetService.getTeamByTeamId(teamId);

        checkLeaderUseCase.validateTeamLeader(member, team);

        String oldProfileImgUrl = team.getProfileImgUrl();
        team.updateTeam(
                UpdateUtils.getUpdatedValue(updateTeamRequest.getName(), team.getName()),
                UpdateUtils.getUpdatedValue(updateTeamRequest.getIntroduction(), team.getIntroduction()),
                UpdateUtils.getUpdatedValue(updateTeamRequest.getProfileImgUrl(), team.getProfileImgUrl())
        );

        updateUtils.deleteOldImgUrl(updateTeamRequest.getProfileImgUrl(), oldProfileImgUrl);

        return new UpdateTeamResponse(team.getTeamId());
    }
}
