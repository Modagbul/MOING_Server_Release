package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.res.*;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.repository.TeamRepository;
import com.moing.backend.domain.team.domain.service.TeamGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveReadUseCase {

    //미션 아치브 읽어오기
    private final MemberGetService memberGetService;
    private final MissionQueryService missionQueryService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final TeamGetService teamGetService;


    // 미션 인증 조회
    public MyMissionArchiveRes getMyArchive(String userSocialId, Long missionId) {

        Long memberId = memberGetService.getMemberBySocialId(userSocialId).getMemberId();

        MyMissionArchiveRes myMissionArchiveRes = new MyMissionArchiveRes();

        List<MissionArchiveRes> missionArchiveRes = MissionArchiveMapper.mapToMissionArchiveResList(missionArchiveQueryService.findMyArchive(memberId, missionId), memberId);
        myMissionArchiveRes.updateArchives(missionArchiveRes);

        myMissionArchiveRes.updateTodayStatus(missionArchiveQueryService.findDoneTodayArchive(memberId, missionId));

        return myMissionArchiveRes;

    }

    // 모두의 미션 인증 목록 조회
    public List<PersonalArchiveRes> getPersonalArchive(String userSocialId, Long missionId) {

        List<PersonalArchiveRes> personalArchives = new ArrayList<>();

        Long memberId = memberGetService.getMemberBySocialId(userSocialId).getMemberId();
        return MissionArchiveMapper.mapToPersonalArchiveList(missionArchiveQueryService.findOthersArchive(memberId, missionId), memberId);
    }

    public MissionArchiveStatusRes getMissionDoneStatus(Long missionId) {
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        return MissionArchiveStatusRes.builder()
                .total(team.getNumOfMember().toString())
                .done(missionArchiveQueryService.findDoneArchives(missionId).toString())
                .build();

    }




}
