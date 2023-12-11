package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageResponse;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import com.moing.backend.domain.mypage.application.mapper.MyPageMapper;
import com.moing.backend.domain.team.domain.constant.Category;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetMyPageUseCase {

    private final MemberGetService memberGetService;
    private final TeamGetService teamGetService;
    private final MyPageMapper myPageMapper;

    @Transactional(readOnly = true)
    public GetMyPageResponse getMyPageResponse(String socialId) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        List<GetMyPageTeamBlock> getMyPageTeamBlocks = teamGetService.getMyPageTeamBlockByMemberId(member.getMemberId());
        return myPageMapper.toGetMyPageResponse(member, calculateCategory(getMyPageTeamBlocks), getMyPageTeamBlocks);
    }

    private List<Category> calculateCategory(List<GetMyPageTeamBlock> getMyPageTeamBlocks) {
        return getMyPageTeamBlocks.stream()
                .map(GetMyPageTeamBlock::getCategory)
                .distinct()
                .limit(2)
                .collect(Collectors.toList());
    }

}
