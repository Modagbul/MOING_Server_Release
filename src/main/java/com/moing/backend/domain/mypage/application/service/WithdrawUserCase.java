package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberDeleteService;
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
    private final MemberDeleteService memberDeleteService;

    public void withdraw(String socialId, WithdrawRequest withdrawRequest) {
        //1. 멤버 삭제
        Member member = memberGetService.getMemberBySocialId(socialId);
        memberDeleteService.deleteMember(member);
        //TODO: 애플과 카카오 회원탈퇴
        //2. 피드백 저장
        feedbackSaveService.saveFeedback(member, withdrawRequest);
        //3. 리프레시 토큰 만료
        tokenUtil.expireRefreshToken(socialId);
    }
}
