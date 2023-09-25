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
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper.mapToPersonalArchive;

@Service
@Transactional
@RequiredArgsConstructor
public class SingleMissionArchiveReadUseCase {

    //미션 아치브 읽어오기
    private final MemberGetService memberGetService;
    private final MissionQueryService missionQueryService;
    private final MissionArchiveQueryService missionArchiveQueryService;


    // 미션 인증 조회
    public MissionArchiveRes getMyArchive(String userSocialId, Long missionId) {

        List<MissionArchiveRes> archiveRes = new ArrayList<>();

        Member member = memberGetService.getMemberBySocialId(userSocialId);

        return MissionArchiveMapper.mapToMissionArchiveRes(missionArchiveQueryService.findMyArchive(member.getMemberId(), missionId).get(0));
    }

    // 모두의 미션 인증 목록 조회
    public List<PersonalArchive> getPersonalArchive(String userSocialId,Long missionId) {

        List<PersonalArchive> personalArchives = new ArrayList<>();

        Member member = memberGetService.getMemberBySocialId(userSocialId);
        return MissionArchiveMapper.mapToPersonalArchiveList(missionArchiveQueryService.findOthersArchive(member.getMemberId(), missionId));

//        return personalArchives;
    }


}
