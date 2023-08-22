package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.domain.service.FeedbackSaveService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawUserCase {
    private final MemberGetService memberGetService;
    private final FeedbackSaveService feedbackSaveService;
    private final TokenUtil tokenUtil;

    public void withdraw(String socialId, WithdrawRequest withdrawRequest) {
        //1. 멤버 상태 변화
        Member member = memberGetService.getMemberBySocialId(socialId);
        member.deleteAccount();
        //2. 피드백 저장
        feedbackSaveService.saveFeedback(member, withdrawRequest);
        //3. 리프레시 토큰 만료
        tokenUtil.expireRefreshToken(socialId);
    }
}
