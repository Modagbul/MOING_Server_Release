package com.moing.backend.domain.block.domain.service;

import com.moing.backend.domain.block.domain.entity.Block;
import com.moing.backend.domain.block.domain.repository.BlockRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class BlockSaveService {

    private final BlockRepository blockRepository;


    public Block save(Block block) {
        return blockRepository.save(block);
    }
}
