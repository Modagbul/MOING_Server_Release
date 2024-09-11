package com.moing.backend.domain.block.application.service;

import com.moing.backend.domain.block.application.mapper.BlockMapper;
import com.moing.backend.domain.block.domain.service.BlockDeleteService;
import com.moing.backend.domain.block.domain.service.BlockQueryService;
import com.moing.backend.domain.block.domain.service.BlockSaveService;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockCreateUseCase {

    private final MemberGetService memberGetService;
    private final BlockSaveService blockSaveService;


    /**
     * 차단 하기
     */

    public Long createBlock(String socialId, Long targetId) {
        Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();

        blockSaveService.save(BlockMapper.mapToBlock(memberId, targetId));

        return targetId;
    }


}
