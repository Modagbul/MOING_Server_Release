package com.moing.backend.global.utils;

import com.moing.backend.domain.member.domain.constant.Gender;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

    /**
     * 테스트용 insert
     */

    private final MemberSaveService memberSaveService;
    @Override
    public void run(String... args) throws Exception {
        Member test01 = new Member(LocalDate.now(), "tester1@test.com", "undef", Gender.WOMAN, "undef", "modagbul_tester1", "undef", SocialProvider.KAKAO, RegistrationStatus.COMPLETED, Role.USER, "KAKAO@tester01");
        memberSaveService.saveMember(test01);

        Member test02=new Member(LocalDate.now(), "tester2@test.com", "undef", Gender.MAN, "undef", "modagbul_tester2", "undef", SocialProvider.KAKAO, RegistrationStatus.COMPLETED, Role.USER, "KAKAO@tester02");
        memberSaveService.saveMember(test02);

        Member test03=new Member(LocalDate.now(), "tester3@test.com", "undef", Gender.WOMAN, "undef", "modagbul_tester3", "undef", SocialProvider.APPLE, RegistrationStatus.COMPLETED, Role.USER, "APPLE@tester03");
        memberSaveService.saveMember(test03);

        Member test04=new Member(LocalDate.now(), "tester4@test.com", "undef", Gender.WOMAN, "undef", "modagbul_tester4", "undef", SocialProvider.APPLE, RegistrationStatus.COMPLETED, Role.USER, "APPLE@tester04");
        memberSaveService.saveMember(test04);

        Member test05=new Member(LocalDate.now(), "tester5@test.com", "undef", Gender.WOMAN, "undef", "modagbul_tester5", "undef", SocialProvider.APPLE, RegistrationStatus.COMPLETED, Role.USER, "APPLE@tester05");
        memberSaveService.saveMember(test05);
        
    }
}
