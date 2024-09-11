package com.moing.backend.domain.member.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@DomainService
@AllArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member) {
        Optional<Member>findMember=memberRepository.findNotDeletedBySocialId(member.getSocialId());
        if(findMember.isEmpty()){
            return memberRepository.save(member);
        } else {
            findMember.get().updateFcmToken(member.getFcmToken());
            findMember.get().updateLastSignInTime(LocalDateTime.now());
            findMember.get().signIn();
            return findMember.get();
        }
    }
}
