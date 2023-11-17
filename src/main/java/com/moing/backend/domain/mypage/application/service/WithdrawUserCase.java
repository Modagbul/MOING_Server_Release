package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.auth.application.service.SignInProvider;
import com.moing.backend.domain.auth.application.service.WithdrawProvider;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberDeleteService;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.domain.service.FeedbackSaveService;
import com.moing.backend.domain.mypage.exception.ExistingTeamException;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.domain.teamMember.domain.service.TeamMemberGetService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WithdrawUserCase {

    private final MemberGetService memberGetService;
    private final FeedbackSaveService feedbackSaveService;
    private final TokenUtil tokenUtil;
    private final MemberDeleteService memberDeleteService;
    private final TeamMemberGetService teamMemberGetService;
    private final TeamGetService teamGetService;
    private final Map<String, WithdrawProvider> withdrawProviders;

    public void withdraw(String socialId, String providerInfo, WithdrawRequest withdrawRequest) throws IOException {
        Member member = memberGetService.getMemberBySocialId(socialId);
        checkMemberIsNotPartOfAnyTeam(member);
        withdraw(providerInfo, withdrawRequest.getSocialToken());
        memberDeleteService.deleteMember(member);
        feedbackSaveService.saveFeedback(member, withdrawRequest);
        tokenUtil.expireRefreshToken(socialId);
    }

    private void withdraw(String providerInfo, String token) throws IOException {
        WithdrawProvider withdrawProvider=withdrawProviders.get(providerInfo+"Withdraw");
        if (withdrawProvider == null) {
            throw new IllegalArgumentException("Unknown provider: " + providerInfo);
        }
        withdrawProvider.withdraw(token);
    }

    private void checkMemberIsNotPartOfAnyTeam(Member member) {
        if (!teamGetService.getTeamIdByMemberId(member.getMemberId()).isEmpty()) {
            throw new ExistingTeamException();
        }
    }
}

