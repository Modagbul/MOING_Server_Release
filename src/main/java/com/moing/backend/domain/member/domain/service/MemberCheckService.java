package com.moing.backend.domain.member.domain.service;

import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
public class MemberCheckService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        return memberRepository.checkNickname(nickname);
    }

}
