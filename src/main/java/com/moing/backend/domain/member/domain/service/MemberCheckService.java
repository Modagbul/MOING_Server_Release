package com.moing.backend.domain.member.domain.service;

import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MemberCheckService {
    private final MemberRepository memberRepository;
    public boolean checkNickname(String nickname) {
        return memberRepository.checkNickname(nickname);
    }

}
