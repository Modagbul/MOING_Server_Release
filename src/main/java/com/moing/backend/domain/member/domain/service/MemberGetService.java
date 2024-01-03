package com.moing.backend.domain.member.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.domain.member.exception.NotFoundBySocialIdException;
import com.moing.backend.domain.statistics.application.dto.DailyStats;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.math.BigInteger;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MemberGetService {
    private final MemberRepository memberRepository;

    public Member getMemberBySocialId(String socialId){
        return memberRepository.findNotDeletedBySocialId(socialId).orElseThrow(()->new NotFoundBySocialIdException());
    }

    public Member getMemberByMemberId(Long memberId) {
        return memberRepository.findNotDeletedByMemberId(memberId).orElseThrow(()->new NotFoundBySocialIdException());
    }

    public DailyStats getDailyStats(){
        return memberRepository.getDailyStats();
    }
}
