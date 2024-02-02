package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.CreateTeamResponse;
import com.moing.backend.domain.team.application.mapper.TeamMapper;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.service.TeamSaveService;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberSaveService;
import com.moing.backend.domain.teamScore.application.mapper.TeamScoreMapper;
import com.moing.backend.domain.teamScore.domain.service.TeamScoreSaveService;
import com.moing.backend.global.config.slack.team.dto.TeamCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateTeamUseCase {

    private final MemberGetService memberGetService;
    private final TeamSaveService teamSaveService;
    private final TeamMemberSaveService teamMemberSaveService;
    private final TeamScoreSaveService teamScoreSaveService;
    private final ApplicationEventPublisher eventPublisher;

    public CreateTeamResponse createTeam(CreateTeamRequest createTeamRequest, String socialId){
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team = createAndSaveTeam(createTeamRequest, member);
        publishTeamCreateEvent(team);
        return new CreateTeamResponse(team.getTeamId());
    }

    private Team createAndSaveTeam(CreateTeamRequest createTeamRequest, Member member) {
        Team team = TeamMapper.createTeam(createTeamRequest, member);
        teamSaveService.saveTeam(team);
        teamMemberSaveService.addTeamMember(team, member);
        team.approveTeam(); // 승인 처리
        teamScoreSaveService.save(TeamScoreMapper.mapToTeamScore(team));
        return team;
    }

    private void publishTeamCreateEvent(Team team) {
        eventPublisher.publishEvent(new TeamCreateEvent(team.getName(), team.getLeaderId()));
    }
}

