package com.moing.backend.domain.member.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@DomainService
@Transactional
@AllArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        if(memberRepository.findBySocialIdNotDeleted(member.getSocialId()).isEmpty()){
            return memberRepository.save(member);
        }
        return memberRepository.findBySocialIdNotDeleted(member.getSocialId()).get();
    }
}
