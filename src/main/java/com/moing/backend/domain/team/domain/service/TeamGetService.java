package com.moing.backend.domain.team.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.missionArchive.application.dto.res.MyTeamsRes;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import com.moing.backend.domain.team.application.dto.response.GetLeaderInfoResponse;
import com.moing.backend.domain.team.application.dto.response.GetNewTeamResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamCountResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.repository.TeamRepository;
import com.moing.backend.domain.team.exception.NotFoundByTeamIdException;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TeamGetService {
    private final TeamRepository teamRepository;

    public GetTeamResponse getTeamByMember(Member member) {
        GetTeamResponse getTeamResponse = teamRepository.findTeamByMemberId(member.getMemberId());
        getTeamResponse.updateMemberNickName(member.getNickName());
        return getTeamResponse;
    }

    public List<Long> getTeamIdByMemberId(Long memberId) {
        return teamRepository.findTeamIdByMemberId(memberId);
    }

    public Team getTeamByTeamId(Long teamId){
        return teamRepository.findTeamByTeamId(teamId).orElseThrow(NotFoundByTeamIdException::new);
    }

    public List<GetMyPageTeamBlock> getMyPageTeamBlockByMemberId(Long memberId){
        return teamRepository.findMyPageTeamByMemberId(memberId);
    }

    public List<MyTeamsRes> getTeamNameByTeamId(List<Long> teamId) {
        return teamRepository.findTeamNameByTeamId(teamId);
    }

    public List<GetLeaderInfoResponse> getLeaderInfoResponses(List<Long> teamIds){
        return teamRepository.findLeaderInfoByTeamIds(teamIds);
    }

    public Page<GetNewTeamResponse> getNewTeams(String dateSort, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, pageable.getPageSize(), Sort.by("no").descending());
        return teamRepository.findNewTeam(dateSort, pageable);
    }

    public GetTeamCountResponse getTeamCountAndName(Long teamId, Long memberId) {

        return teamRepository.findTeamCount(memberId, teamId);
    }

}
