package com.moing.backend.domain.member.domain.repository;

import com.moing.backend.domain.member.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberCustomRepository {

    boolean checkNickname(String nickname);
    Optional<Member> findNotDeletedBySocialId(String socialId);
    Optional<Member> findNotDeletedByEmail(String email);
    Optional<Member> findNotDeletedByMemberId(Long id);
    Long getTodayNewMembers();
    Long getYesterdayNewMembers();

    Optional<List<Member>> findAllMemberOnPushAlarm(Long offset, Long limit);
}
