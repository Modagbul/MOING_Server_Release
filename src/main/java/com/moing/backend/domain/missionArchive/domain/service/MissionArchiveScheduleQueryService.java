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

    }


}
