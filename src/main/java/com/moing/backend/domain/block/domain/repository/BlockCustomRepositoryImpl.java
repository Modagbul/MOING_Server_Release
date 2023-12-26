package com.moing.backend.domain.block.domain.repository;

import com.moing.backend.domain.block.domain.entity.Block;
import com.moing.backend.domain.block.domain.entity.QBlock;
import com.moing.backend.domain.member.domain.entity.QMember;
import com.moing.backend.domain.report.application.dto.BlockMemberRes;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.block.domain.entity.QBlock.block;
import static com.moing.backend.domain.member.domain.entity.QMember.member;

public class BlockCustomRepositoryImpl implements BlockCustomRepository {

    private final JPAQueryFactory queryFactory;

    public BlockCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Optional<List<Long>> getMyBlockList(Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(block.targetId)
                .from(block)
                .where(block.blockMemberId.eq(memberId))
                .fetch());
    }
    @Override
    public Optional<List<BlockMemberRes>> getMyBlockInfoList(Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(BlockMemberRes.class,
                        block.targetId,
                        member.nickName,
                        member.introduction,
                        member.profileImage
                        ))
                .from(block)
                    .join(member)
                        .on(member.memberId.eq(block.targetId))
                .where(block.blockMemberId.eq(memberId))
                .fetch());
    }

    @Override
    public Optional<Block> getBlockById(Long memberId, Long targetId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(block)
                .where(
                        block.blockMemberId.eq(memberId),
                        block.targetId.eq(targetId)).fetchFirst());

    }
}
