package com.moing.backend.global.util;

import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.member.domain.service.MemberSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

    /**
     * 테스트용 insert
     */

    private final MemberSaveService memberSaveService;
    @Override
    public void run(String... args) throws Exception {
        Member test01 = new Member("undef", "tester1@test.com", "undef", "undef", "undef", "modagbul_tester1", "undef", SocialProvider.KAKAO, RegistrationStatus.COMPLETED, Role.USER, "KAKAO@tester01");
        memberSaveService.saveMember(test01);

        Member test02=new Member("undef", "tester2@test.com", "undef", "undef", "undef", "modagbul_tester2", "undef", SocialProvider.KAKAO, RegistrationStatus.COMPLETED, Role.USER, "KAKAO@tester02");
        memberSaveService.saveMember(test02);

        Member test03=new Member("undef", "tester3@test.com", "undef", "undef", "undef", "modagbul_tester3", "undef", SocialProvider.APPLE, RegistrationStatus.COMPLETED, Role.USER, "APPLE@tester03");
        memberSaveService.saveMember(test03);
    }
}
