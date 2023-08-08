package com.moing.backend.domain.member.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@DomainService
@Transactional
@AllArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        Optional<Member>findMember=memberRepository.findBySocialIdNotDeleted(member.getSocialId());
        if(findMember.isEmpty()){
            return memberRepository.save(member);
        } else {
            return findMember.get();
        }
    }
}
