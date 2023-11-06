package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.missionArchive.application.dto.res.MyTeamsRes;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import com.moing.backend.domain.team.application.dto.response.GetTeamDetailResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.domain.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamCustomRepository {
    GetTeamResponse findTeamByMemberId(Long memberId);
    Optional<Team> findTeamByTeamId(Long TeamId);
    List<Long> findTeamIdByMemberId(Long memberId);
    List<GetMyPageTeamBlock> findMyPageTeamByMemberId(Long memberId);
    List<MyTeamsRes> findTeamNameByTeamId(List<Long> teamId);
}
