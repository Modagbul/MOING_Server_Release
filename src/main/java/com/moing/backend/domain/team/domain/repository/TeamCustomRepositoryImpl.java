package com.moing.backend.domain.team.domain.repository;

import com.moing.backend.domain.missionArchive.application.dto.res.MyTeamsRes;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import com.moing.backend.domain.team.application.dto.response.*;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.entity.Team;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.member.domain.entity.QMember.member;
import static com.moing.backend.domain.team.domain.entity.QTeam.team;
import static com.moing.backend.domain.teamMember.domain.entity.QTeamMember.teamMember;

public class TeamCustomRepositoryImpl implements TeamCustomRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public TeamCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
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
    public Optional<Team> findTeamIncludeDeletedByTeamId(Long teamId){
        return Optional.ofNullable(queryFactory.selectFrom(team)
                .where(team.teamId.eq(teamId))
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
                        team.teamId, team.name, team.category, team.profileImgUrl))
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
                        team.deletionTime,
                        team.profileImgUrl))
                .from(teamMember)
                .innerJoin(teamMember.team, team)
                .on(teamMember.member.memberId.eq(memberId))
                .where(team.approvalStatus.eq(ApprovalStatus.APPROVAL)) // 승인 되었고
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

    @Override
    public void updateTeamStatus(boolean isApproved, List<Long> teamIds) {
        ApprovalStatus approvalStatus = isApproved ? ApprovalStatus.APPROVAL : ApprovalStatus.REJECTION;

        JPAUpdateClause updateClause = queryFactory
                .update(team)
                .set(team.approvalStatus, approvalStatus);

        // 승인되었을 때만 현재 시간으로 approvalTime 설정
        if (isApproved) {
            updateClause.set(team.approvalTime, LocalDateTime.now());
        }

        updateClause
                .where(team.teamId.in(teamIds))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public List<GetLeaderInfoResponse> findLeaderInfoByTeamIds(List<Long> teamIds) {
        return queryFactory
                .select(Projections.constructor(GetLeaderInfoResponse.class,
                        team.teamId,
                        team.name,
                        member.memberId,
                        member.nickName,
                        member.fcmToken))
                .from(team)
                .join(member).on(team.leaderId.eq(member.memberId))
                .where(team.teamId.in(teamIds))
                .fetch();
    }

    @Override
    public Page<GetNewTeamResponse> findNewTeam(String dateSort, Pageable pageable) {
        OrderSpecifier<?> orderBy = team.createdDate.asc();
        if ("desc".equals(dateSort)) {
            orderBy = team.createdDate.desc();
        }

        List<GetNewTeamResponse> teams = queryFactory
                .select(Projections.constructor(GetNewTeamResponse.class,
                        team.name, team.category, team.promise, team.introduction, team.profileImgUrl, team.createdDate))
                .from(team)
                .where(team.approvalStatus.eq(ApprovalStatus.NO_CONFIRMATION))
                .orderBy(orderBy)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(team.count())
                .from(team)
                .where(team.approvalStatus.eq(ApprovalStatus.NO_CONFIRMATION))
                .fetchOne();

        return PageableExecutionUtils.getPage(teams, pageable, () -> count);
    }

    @Override
    public GetTeamCountResponse findTeamCount(Long memberId, Long teamId) {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        Long count=queryFactory
                .select(team.count()) // 팀의 개수를 세기 위해 수정
                .from(teamMember)
                .innerJoin(teamMember.team, team)
                .on(teamMember.member.memberId.eq(memberId))
                .where(team.approvalStatus.eq(ApprovalStatus.APPROVAL)) // 승인 되었고
                .where(teamMember.isDeleted.eq(false) // 탈퇴하지 않았다면
                        .and(team.isDeleted.eq(false) // 강제종료되지 않았거나
                                .or(team.deletionTime.after(threeDaysAgo)))) // 강제종료된 경우 3일이 지나지 않았다면
                .fetchOne(); // 단일 결과 (개수) 반환

        if (count == null) {
            count = 0L; // null인 경우 0으로 처리
        }

        GetTeamCountResponse response=queryFactory
                .select(new QGetTeamCountResponse(team.name, member.nickName))
                .from(team)
                .join(member).on(team.leaderId.eq(member.memberId))
                .where(team.teamId.eq(teamId))
                .fetchOne();

        if (response == null) {
            // response가 null인 경우, 적절한 기본값 설정 또는 예외 처리
            response = new GetTeamCountResponse("기본 팀 이름", "기본 멤버 닉네임");
            response.updateCount(0L);
        } else {
            response.updateCount(count);
        }

        return response;
    }


}
