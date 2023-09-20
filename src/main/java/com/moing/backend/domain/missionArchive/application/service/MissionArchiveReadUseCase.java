package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchive;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper.mapToPersonalArchive;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveReadUseCase {

    //미션 아치브 읽어오기
    private final MemberGetService memberGetService;
    private final MissionQueryService missionQueryService;
    private final MissionArchiveQueryService missionArchiveQueryService;


    // 나의 인증 목록
    public MissionArchiveRes getMyArchive(String userSocialId, Long missionId) {
        MissionArchive myArchive = getArchive(userSocialId, missionId);
        return MissionArchiveMapper.mapToMissionArchiveRes(myArchive);
    }

    // 팀원들 인증 목록
    public List<PersonalArchive> getPersonalArchive(String userSocialId, Long missionId) {
        Mission mission = missionQueryService.findMissionById(missionId);

        List<PersonalArchive> done = new ArrayList<>();

        mission.getMissionArchiveList()
                .forEach(missionArchive1 -> done.add(mapToPersonalArchive(missionArchive1)));

        return done;
    }

    // memberId, missionId로 archive 꺼내기
    public MissionArchive getArchive(String userSocialId, Long missionId) {
        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission mission = missionQueryService.findMissionById(missionId);

        return missionArchiveQueryService.findMyArchive(member.getMemberId(), missionId);
    }


}