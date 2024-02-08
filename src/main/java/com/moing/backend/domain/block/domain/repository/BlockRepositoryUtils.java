package com.moing.backend.domain.block.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;

import static com.moing.backend.domain.block.domain.entity.QBlock.block;

public class BlockRepositoryUtils {
    public static BooleanExpression blockCondition(Long memberId, NumberPath<Long> targetMemberId) {
        return JPAExpressions
                .select(block.id)
                .from(block)
                .where(block.blockMemberId.eq(memberId),
                        block.targetId.eq(targetMemberId))
                .notExists();
    }

    public static BooleanExpression blockCondition(NumberPath<Long> memberId, Long targetMemberId) {
        return JPAExpressions
                .select(block.id)
                .from(block)
                .where(block.blockMemberId.eq(memberId),
                        block.targetId.eq(targetMemberId))
                .notExists();
    }
}
