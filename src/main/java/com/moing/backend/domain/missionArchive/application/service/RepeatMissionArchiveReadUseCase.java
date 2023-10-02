package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveStatusRes;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RepeatMissionArchiveReadUseCase {

    //미션 아치브 읽어오기
    private final MemberGetService memberGetService;
    private final MissionQueryService missionQueryService;
    private final MissionArchiveQueryService missionArchiveQueryService;


//    // 나의 인증 목록
//    public List<MissionArchiveRes> getMyArchive(String userSocialId, Long missionId) {
//        List<MissionArchive> myArchives = getArchive(userSocialId, missionId);
//
//        List<MissionArchiveRes> archiveRes = new ArrayList<>();
//        myArchives.forEach(myArchive -> archiveRes.add(MissionArchiveMapper.mapToMissionArchiveRes(myArchive)));
//
//        return archiveRes;
//    }
//
//    // 팀원들 인증 목록
//    public List<PersonalArchive> getPersonalArchive(String userSocialId, Long missionId) {
//        Mission mission = missionQueryService.findMissionById(missionId);
//
//        List<PersonalArchive> done = new ArrayList<>();
//
//        mission.getMissionArchiveList()
//                .forEach(missionArchive1 -> done.add(mapToPersonalArchive(missionArchive1)));
//
//        return done;
//    }
//
//    // memberId, missionId로 archive 꺼내기
//    public List<MissionArchive> getArchive(String userSocialId, Long missionId) {
//        Member member = memberGetService.getMemberBySocialId(userSocialId);
//        Mission mission = missionQueryService.findMissionById(missionId);
//
//        return missionArchiveQueryService.findMyArchive(member.getMemberId(), missionId);
//    }

    public MissionArchiveStatusRes getMyMissionDoneStatus(String userSocialId,Long missionId) {
        Member member = memberGetService.getMemberBySocialId(userSocialId);
        Mission mission = missionQueryService.findMissionById(missionId);
        Team team = mission.getTeam();

        return MissionArchiveStatusRes.builder()
                .total(String.valueOf(mission.getNumber()))
                .done(missionArchiveQueryService.findMyDoneArchives(member.getMemberId(),missionId).toString())
                .build();

    }


}
