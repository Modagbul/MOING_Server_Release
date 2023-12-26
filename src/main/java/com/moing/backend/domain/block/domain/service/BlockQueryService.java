package com.moing.backend.domain.block.domain.service;

import com.moing.backend.domain.block.domain.entity.Block;
import com.moing.backend.domain.block.domain.repository.BlockRepository;
import com.moing.backend.domain.block.exception.NotFoundBlockException;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class BlockQueryService {

    private final BlockRepository blockRepository;

    public List<Long> getBlockLists(Long memberId) {
        return blockRepository.getMyBlockList(memberId).orElseThrow(NotFoundBlockException::new);
    }

    public Block getBlock(Long memberId, Long targetId) {
        return blockRepository.getBlockById(memberId, targetId).orElseThrow(NotFoundBlockException::new);
    }
}
