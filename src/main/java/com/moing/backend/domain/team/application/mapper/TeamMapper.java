package com.moing.backend.domain.team.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.*;
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

    public GetTeamDetailResponse toTeamDetailResponse(Long memberId, Team team, Integer boardNum, List<TeamMemberInfo> teamMemberInfoList) {
        TeamInfo teamInfo = new TeamInfo(team.isDeleted(), team.getDeletionTime(), team.getName(), teamMemberInfoList.size(), team.getCategory(), team.getIntroduction(), memberId, teamMemberInfoList);
        return GetTeamDetailResponse.builder()
                .boardNum(boardNum)
                .teamInfo(teamInfo)
                .build();
    }

    public ReviewTeamResponse toReviewTeamResponse(Long numOfMission, Team team, boolean isLeader, String memberName){
        return ReviewTeamResponse
                .builder()
                .teamId(team.getTeamId())
                .teamName(team.getName())
                .numOfMember(team.getNumOfMember())
                .levelOfFire(team.getLevelOfFire())
                .duration(calculateDuration(team.getApprovalTime()))
                .numOfMission(numOfMission)
                .isLeader(isLeader)
                .memberName(memberName)
                .build();
    }

    public Long calculateDuration(LocalDateTime approvalTime) {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime currentDateTime = LocalDateTime.now(seoulZoneId);

        long hoursBetween = ChronoUnit.HOURS.between(approvalTime, currentDateTime);
        long daysBetween = hoursBetween / 24;

        return daysBetween;
    }

    public GetCurrentStatusResponse toCurrentStatusResponse(Team team) {
        return GetCurrentStatusResponse.builder()
                .name(team.getName())
                .introduction(team.getIntroduction())
                .profileImgUrl(team.getProfileImgUrl())
                .build();
    }

}
