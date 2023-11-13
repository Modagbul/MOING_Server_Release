package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.missionArchive.application.dto.res.MyTeamsRes;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import com.moing.backend.domain.team.application.dto.response.GetTeamResponse;
import com.moing.backend.domain.team.application.dto.response.QTeamBlock;
import com.moing.backend.domain.team.application.dto.response.TeamBlock;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.entity.Team;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
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
                .groupBy(team.teamId)
                .orderBy(team.approvalTime.asc())
                .fetch();
    }

    @Override
    public List<GetMyPageTeamBlock> findMyPageTeamByMemberId(Long memberId) {
        return queryFactory
                .select(Projections.constructor(GetMyPageTeamBlock.class,
                        team.teamId, team.name, team.category))
                .from(teamMember)
                .innerJoin(teamMember.team, team)
                .on(teamMember.member.memberId.eq(memberId))
                .where(team.approvalStatus.eq(ApprovalStatus.APPROVAL)) // 승인 되었고
                .orderBy(team.missions.size().desc())
                .groupBy(team.teamId)
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
                .where(teamMember.isDeleted.eq(false)// 탈퇴하지 않았다면
                        .and(team.isDeleted.eq(false) // 강제종료되지 않았거나
                                .or(team.deletionTime.after(threeDaysAgo))))// 강제종료된 경우 3일이 지나지 않았다면
                .orderBy(team.approvalTime.asc())
                .groupBy(team.teamId)
                .fetch();
    }

    @Override
    public List<MyTeamsRes> findTeamNameByTeamId(List<Long> teamId) {
        return queryFactory
                .select(Projections.constructor(MyTeamsRes.class,
                        team.teamId,
                        team.name))
                .from(team)
                .where(team.teamId.in(teamId))
                .fetch();
    }


}
