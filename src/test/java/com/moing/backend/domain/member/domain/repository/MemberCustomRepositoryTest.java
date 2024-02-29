//package com.moing.backend.domain.member.domain.repository;
//
//import com.moing.backend.domain.member.domain.entity.Member;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("dev")
//@Transactional
//class MemberCustomRepositoryTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Test
//    void findAllMemberOnPushAlarm() {
//        List<Member> members = memberRepository.findAllMemberOnPushAlarm().orElseThrow();
//        for (Member member : members) {
//            System.out.println(member.getNickName());
//        }
//
//    }
//}