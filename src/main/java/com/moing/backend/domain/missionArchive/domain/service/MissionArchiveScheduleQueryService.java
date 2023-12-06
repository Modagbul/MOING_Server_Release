package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionArchiveScheduleQueryService {


    private final MissionArchiveRepository missionArchiveRepository;

    public List<String> getRemainMissionPeople() {
        return missionArchiveRepository.findPeopleRemainMission().orElseThrow();
    }


}
