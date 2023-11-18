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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateTeamUserCase {

    private final MemberGetService memberGetService;
    private final TeamSaveService teamSaveService;
    private final TeamMemberSaveService teamMemberSaveService;
    private final TeamMapper teamMapper;
    private final TeamScoreSaveService teamScoreSaveService;
    private final TeamScoreMapper teamScoreMapper;

    public CreateTeamResponse createTeam(CreateTeamRequest createTeamRequest, String socialId){
        Member member = memberGetService.getMemberBySocialId(socialId);
        Team team=teamMapper.createTeam(createTeamRequest, member);
        teamSaveService.saveTeam(team);
        teamMemberSaveService.addTeamMember(team, member);
        //====지워야 함 (테스트 용)=====
        team.approveTeam();
        //====지워야 함 (테스트 용)=====
        teamScoreSaveService.save(teamScoreMapper.mapToTeamScore(team)); // 팀스코어 엔티티 생성
        return new CreateTeamResponse(team.getTeamId());
    }
}
