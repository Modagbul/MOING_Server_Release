package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.missionArchive.application.dto.res.MyTeamsRes;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import com.moing.backend.domain.team.application.dto.response.GetLeaderInfoResponse;
import com.moing.backend.domain.team.application.dto.response.GetNewTeamResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamCountResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TeamCustomRepository {
    GetTeamResponse findTeamByMemberId(Long memberId);
    Optional<Team> findTeamByTeamId(Long TeamId);
    Optional<Team> findTeamIncludeDeletedByTeamId(Long teamId);
    List<Long> findTeamIdByMemberId(Long memberId);
    List<GetMyPageTeamBlock> findMyPageTeamByMemberId(Long memberId);
    List<MyTeamsRes> findTeamNameByTeamId(List<Long> teamId);
    void updateTeamStatus(boolean isApproved, List<Long> teamIds);
    List<GetLeaderInfoResponse> findLeaderInfoByTeamIds(List<Long> teamIds);
    Page<GetNewTeamResponse> findNewTeam(String dateSort, Pageable pageable);
    GetTeamCountResponse findTeamCount(Long memberId, Long teamId);
}
