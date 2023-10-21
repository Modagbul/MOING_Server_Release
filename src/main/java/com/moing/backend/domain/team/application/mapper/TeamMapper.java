package com.moing.backend.domain.team.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.DeleteTeamResponse;
import com.moing.backend.domain.team.application.dto.response.GetTeamDetailResponse;
import com.moing.backend.domain.team.application.dto.response.TeamInfo;
import com.moing.backend.domain.team.application.dto.response.TeamMemberInfo;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.constant.Category;
import com.moing.backend.domain.team.domain.entity.Team;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class TeamMapper {

    public Team createTeam(CreateTeamRequest createTeamRequest, Member member) {
        return Team.builder()
                .category(Enum.valueOf(Category.class, createTeamRequest.getCategory()))
                .name(createTeamRequest.getName())
                .introduction(createTeamRequest.getIntroduction())
                .promise(createTeamRequest.getPromise())
                .profileImgUrl(createTeamRequest.getProfileImgUrl())
                .approvalStatus(ApprovalStatus.NO_CONFIRMATION)
                .leaderId(member.getMemberId())
                .numOfMember(0)
                .levelOfFire(1)
                .build();
    }

    public GetTeamDetailResponse toTeamDetailResponse(Team team, Integer boardNum, List<TeamMemberInfo> teamMemberInfoList) {
        TeamInfo teamInfo = new TeamInfo(team.getName(), teamMemberInfoList.size(), team.getCategory(), team.getIntroduction(), teamMemberInfoList);
        return GetTeamDetailResponse.builder()
                .boardNum(boardNum)
                .teamInfo(teamInfo)
                .build();
    }

    public DeleteTeamResponse toDeleteTeamResponse(Long numOfMission, Team team){
        return DeleteTeamResponse.builder()
                .teamId(team.getTeamId())
                .teamName(team.getName())
                .numOfMember(team.getNumOfMember())
                .levelOfFire(team.getLevelOfFire())
                .duration(calculateDuration(team.getApprovalTime()))
                .numOfMission(numOfMission)
                .build();
    }

    private Long calculateDuration(LocalDateTime approvalTime) {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime currentSeoulTime = LocalDateTime.now(seoulZoneId).withSecond(0).withNano(0);
        LocalDateTime adjustedApprovalTime = approvalTime.withSecond(0).withNano(0);
        return ChronoUnit.DAYS.between(adjustedApprovalTime, currentSeoulTime);
    }
}
