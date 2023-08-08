package com.moing.backend.domain.auth.application.service;

import com.moing.backend.domain.auth.exception.AccountAlreadyExistedException;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAuthUserCase {

    private final MemberSaveService memberSaveService;

    @Transactional
    public Member auth(Member member, String providerInfo) {
        Member signInMember = memberSaveService.saveMember(member);
        checkRegistration(signInMember, providerInfo);
        return signInMember;
    }


    // 다른 플랫폼으로 가입했으면 에러 출력
    private void checkRegistration(Member signInMember, String providerInfo) {
        Optional.of(signInMember)
                .filter(m -> providerInfo.contains(m.getProvider().name().toLowerCase()))
                .orElseThrow(()->new AccountAlreadyExistedException());
    }

}
