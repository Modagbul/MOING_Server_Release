package com.moing.backend.domain.block.domain.service;

import com.moing.backend.domain.block.domain.entity.Block;
import com.moing.backend.domain.block.domain.repository.BlockRepository;
import com.moing.backend.domain.block.exception.NotFoundBlockException;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BlockDeleteService {
    private final BlockRepository blockRepository;

    public void delete(Long memberId, Long targetId) {
        Block block = blockRepository.getBlockById(memberId, targetId).orElseThrow(NotFoundBlockException::new);
        blockRepository.delete(block);
    }
}
