package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberDeleteService;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.domain.service.FeedbackSaveService;
import com.moing.backend.domain.mypage.exception.ExistingTeamException;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawUserCase {

    private final MemberGetService memberGetService;
    private final FeedbackSaveService feedbackSaveService;
    private final TokenUtil tokenUtil;
    private final MemberDeleteService memberDeleteService;
    private final TeamMemberGetService teamMemberGetService;

    public void withdraw(String socialId, WithdrawRequest withdrawRequest) {
        Member member = memberGetService.getMemberBySocialId(socialId);
        checkMemberIsNotPartOfAnyTeam(member);
        memberDeleteService.deleteMember(member);
        feedbackSaveService.saveFeedback(member, withdrawRequest);
        tokenUtil.expireRefreshToken(socialId);
    }

    private void checkMemberIsNotPartOfAnyTeam(Member member) {
        if (!teamMemberGetService.getNotDeletedTeamMember(member.getMemberId()).isEmpty()) {
            throw new ExistingTeamException();
        }
    }
}

