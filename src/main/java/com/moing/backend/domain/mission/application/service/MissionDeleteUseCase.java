package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.mission.domain.service.MissionDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionDeleteUseCase {

    private final MissionDeleteService missionDeleteService;
    private final MemberGetService memberGetService;

    public Long deleteMission(String userSocialId,Long missionId) {

        Member member = memberGetService.getMemberBySocialId(userSocialId);

        return missionDeleteService.deleteMission(missionId);
    }

}
