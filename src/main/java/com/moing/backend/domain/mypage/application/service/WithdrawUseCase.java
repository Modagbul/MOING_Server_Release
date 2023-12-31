package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.auth.application.service.WithdrawProvider;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.domain.service.FeedbackSaveService;
import com.moing.backend.domain.mypage.exception.ExistingTeamException;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WithdrawUseCase {

    private final MemberGetService memberGetService;
    private final FeedbackSaveService feedbackSaveService;
    private final TokenUtil tokenUtil;
    private final TeamGetService teamGetService;
    private final Map<String, WithdrawProvider> withdrawProviders;

    @Transactional
    public void withdraw(String socialId, String providerInfo, WithdrawRequest withdrawRequest) throws IOException {
        Member member = memberGetService.getMemberBySocialId(socialId);
        checkMemberIsNotPartOfAnyTeam(member);
        socialWithdraw(providerInfo, withdrawRequest.getSocialToken());
        member.deleteMember();
        feedbackSaveService.saveFeedback(member, withdrawRequest);
        tokenUtil.expireRefreshToken(socialId);
    }

    private void socialWithdraw(String providerInfo, String token) throws IOException {
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

