package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.member.domain.entity.QMember;
import com.moing.backend.domain.team.application.dto.response.*;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.entity.Team;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Team> findTeamByTeamId(Long teamId) {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        return Optional.ofNullable(queryFactory.selectFrom(team)
                .where(team.teamId.eq(teamId))
                .where(team.isDeleted.eq(false) // 강제종료되지 않았거나
                        .or(team.deletionTime.after(threeDaysAgo))) // 강제종료된 경우 3일이 지나지 않았다면
                .fetchOne());
    }

    @Override
    public List<Long> findTeamIdByMemberId(Long memberId){
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        return queryFactory
                .select(team.teamId)
                .from(teamMember)
                .innerJoin(teamMember.team, team)
                .on(teamMember.member.memberId.eq(memberId))
                .where(team.approvalStatus.eq(ApprovalStatus.APPROVAL)) // 승인 되었고
                .where(teamMember.isDeleted.eq(false)) // 탈퇴하지 않았다면
                .where(team.isDeleted.eq(false) // 강제종료되지 않았거나
                        .or(team.deletionTime.after(threeDaysAgo))) // 강제종료된 경우 3일이 지나지 않았다면
                .orderBy(team.approvalTime.asc())
                .fetch();
    }

    private List<TeamBlock> getTeamBlock(Long memberId) {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        return queryFactory
                .select(new QTeamBlock(team.teamId,
                        team.approvalTime,
                        team.levelOfFire,
                        team.name,
                        team.numOfMember,
                        team.category,
                        team.deletionTime))
                .from(teamMember)
                .innerJoin(teamMember.team, team)
                .on(teamMember.member.memberId.eq(memberId))
                .where(teamMember.isDeleted.eq(false)) // 탈퇴하지 않았다면
                .where(team.isDeleted.eq(false) // 강제종료되지 않았거나
                        .or(team.deletionTime.isNotNull()
                        .or(team.deletionTime.after(threeDaysAgo)))) // 강제종료된 경우 3일이 지나지 않았다면
                .orderBy(team.approvalTime.asc())
                .fetch();
    }

}
