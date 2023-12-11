package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SignOutUseCase {

    private final TokenUtil tokenUtil;
    private final MemberGetService memberGetService;

    @Transactional
    public void signOut(String socialId){
        tokenUtil.expireRefreshToken(socialId);
        Member member=memberGetService.getMemberBySocialId(socialId);
        member.signOut();
    }
}
