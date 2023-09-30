package com.moing.backend.domain.team.application.mapper;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.constant.Category;
import com.moing.backend.domain.team.domain.entity.Team;
import org.springframework.stereotype.Component;

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
}
