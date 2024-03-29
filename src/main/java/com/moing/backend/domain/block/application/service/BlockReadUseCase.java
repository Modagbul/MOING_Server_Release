package com.moing.backend.domain.block.application.service;

import com.moing.backend.domain.block.domain.service.BlockQueryService;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.report.application.dto.BlockMemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockReadUseCase {

    private final MemberGetService memberGetService;
    private final BlockQueryService blockQueryService;

    /**
     * 차단한 리스트 조회하기
     */
    public List<Long> getMyBlockList(String socialId) {
        Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();

        return blockQueryService.getBlockLists(memberId);
    }
     public List<BlockMemberRes> getMyBlockInfoList(String socialId) {
            Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();

            return blockQueryService.getBlockInfoLists(memberId);
     }

}
