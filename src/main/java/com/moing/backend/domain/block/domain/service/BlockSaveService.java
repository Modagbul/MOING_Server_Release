package com.moing.backend.domain.block.domain.service;

import com.moing.backend.domain.block.domain.entity.Block;
import com.moing.backend.domain.block.domain.repository.BlockRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public class BlockSaveService {

    private final BlockRepository blockRepository;


    public Block save(Block block) {
        //기존에 동일한 사람이 차단했으면 중복 제거
        Optional<Block> findBlock=blockRepository.getBlockById(block.getBlockMemberId(), block.getTargetId());
        return findBlock.orElseGet(() -> blockRepository.save(block));
    }
}
