package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionArchiveScheduleQueryService {


    private final MissionArchiveRepository missionArchiveRepository;

    public List<Member> getRemainMissionPeople() {
        return missionArchiveRepository.findHavingRemainMissionsByQuerydsl().orElseThrow();

//        return Optional.of(maps.stream()
//                .map(map -> {
//                    Object memberIdObject = map.get("memberId");
//                    Long memberId = ((BigInteger) memberIdObject).longValue();
//
//                    Object fcmObject = map.get("fcmToken");
//                    String fcmToken = fcmObject.toString();
//
//                    return MemberIdAndToken.builder()
//                            .fcmToken(fcmToken)
//                            .memberId(memberId)
//                            .build();
//                })
//                .collect(Collectors.toList()));

//        return null;

//        return maps.stream()
//                .map(map -> {
//                    String fcmToken = Optional.ofNullable(map.get("fcmToken")).map(Object::toString).orElse("undef");
//                    Long memberId = map.get("memberId");
//
//                    return MemberIdAndToken.builder()
//                            .fcmToken(fcmToken)
//                            .memberId(memberId)
//                            .build();
//
//                })
//                .collect(Collectors.toList());

    }


}
