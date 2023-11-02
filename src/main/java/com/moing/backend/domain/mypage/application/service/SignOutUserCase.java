package com.moing.backend.domain.mypage.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignOutUserCase {

    private final TokenUtil tokenUtil;
    private final MemberGetService memberGetService;

    public void signOut(String socialId){
        tokenUtil.expireRefreshToken(socialId);
    }
}
