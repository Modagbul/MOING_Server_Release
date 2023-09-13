package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.application.dto.response.QTeamBlock;
import com.moing.backend.domain.team.application.dto.response.TeamBlock;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.moing.backend.domain.team.domain.entity.QTeam.team;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

public class TeamCustomRepositoryImpl implements TeamCustomRepository {

    private final JPAQueryFactory queryFactory;

    public TeamCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public GetTeamResponse findTeamByMemberId(Long memberId) {
        List<TeamBlock> teamBlocks = getTeamBlock(memberId);
        Integer numOfTeam = teamBlocks.size();
        return new GetTeamResponse(numOfTeam, teamBlocks);
    }

    private List<TeamBlock> getTeamBlock(Long memberId) {
        return queryFactory
                .select(new QTeamBlock(team.teamId,
                        team.approvalTime,
                        team.levelOfFire,
                        team.name,
                        team.numOfMember,
                        team.category))
                .from(teamMember)
                .innerJoin(teamMember.team, team)
                .on(teamMember.member.memberId.eq(memberId))
                .where(team.approvalStatus.eq(ApprovalStatus.APPROVAL)) //승인 되었고
                .where(team.isDeleted.eq(false)) //강제종료되었는지
                .where(teamMember.isDeleted.eq(false)) //탈퇴했는지
                .orderBy(team.approvalTime.asc())
                .fetch();
    }

}
