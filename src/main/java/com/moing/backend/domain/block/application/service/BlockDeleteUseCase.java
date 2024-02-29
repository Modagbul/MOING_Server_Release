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
public class BlockDeleteUseCase {

    private final MemberGetService memberGetService;
    private final BlockDeleteService blockDeleteService;


    /**
     * 차단 철회하기
     */

    public Long deleteBlock(String socialId, Long targetId) {
        Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();
        blockDeleteService.delete(memberId, targetId);

        return targetId;
    }

}
