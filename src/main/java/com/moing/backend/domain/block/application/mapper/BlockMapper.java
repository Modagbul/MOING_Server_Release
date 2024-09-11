package com.moing.backend.domain.block.application.mapper;

import com.moing.backend.domain.block.domain.entity.Block;
import com.moing.backend.global.annotation.Mapper;

@Mapper
public class BlockMapper {

    public static Block mapToBlock(Long memberId, Long targetId) {
        return Block.builder()
                .blockMemberId(memberId)
                .targetId(targetId)
                .build();
    }
}
