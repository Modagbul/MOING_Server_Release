package com.moing.backend.domain.member.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.domain.member.exception.NotFoundBySocialIdException;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MemberGetService {
    private final MemberRepository memberRepository;

    public Member getMemberBySocialId(String socialId){
        return memberRepository.findBySocialId(socialId).orElseThrow(()->new NotFoundBySocialIdException());
    }

    public Member getMemberByMemberId(Long memberId) {
        return memberRepository.findByMemberId(memberId).orElseThrow(()->new NotFoundBySocialIdException());
    }
}
