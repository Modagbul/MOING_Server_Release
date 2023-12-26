package com.moing.backend.domain.block.domain.repository;

import com.moing.backend.domain.block.domain.entity.Block;
import com.moing.backend.domain.report.application.dto.BlockMemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface BlockCustomRepository{

    Optional<List<Long>> getMyBlockList(Long memberId);
    Optional<List<BlockMemberRes>> getMyBlockInfoList(Long memberId);
    Optional<Block> getBlockById(Long memberId, Long targetId);

    }
