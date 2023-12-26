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
public class BlockReadUseCase {

    private final MemberGetService memberGetService;
    private final BoardGetService boardGetService;
    private final MissionArchiveQueryService missionArchiveQueryService;

    private final BlockQueryService blockQueryService;

    /**
     * 차단한 리스트 조회하기
     */
    public List<Long> getMyBlockList(String socialId) {
        Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();

        return blockQueryService.getBlockLists(memberId);
    }

}
