package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// 모아보기
@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveBoardUseCase {

    private final MissionArchiveQueryService missionArchiveQueryService;

    private final MemberGetService memberGetService;
    private final TeamRepository teamRepository;

    /*
     * 팀별 미션 보드 중 진행중인 단일 미션(한 번 인증)
     */
    public List<SingleMissionBoardRes> getActiveSingleMissions(Long teamId, String memberId) {

        Member member = memberGetService.getMemberBySocialId(memberId);

        List<MissionArchive> mySingleMissionArchives = missionArchiveQueryService.findMySingleMissionArchives(member.getMemberId(), teamId, MissionStatus.ONGOING);
        return MissionArchiveMapper.mapToSingleMissionBoardResList(mySingleMissionArchives);

    }

    /*
     * 팀별 미션 보드 중 종료한 전체 미션
     */
    public List<FinishMissionBoardRes> getFinishMissions(Long teamId, String memberId) {

        Team team = teamRepository.findById(teamId).orElseThrow();
        Member member = memberGetService.getMemberBySocialId(memberId);

        List<MissionArchive> myAllMissionArchives = missionArchiveQueryService.findMyAllMissionArchives(member.getMemberId(), teamId, MissionStatus.END);
        return MissionArchiveMapper.mapToFinishMissionBoardResList(myAllMissionArchives);

    }

//    public List<RepeatMissionBoardRes> getActiveRepeatMissions(Long teamId, String memberId) {
//
//        Team team = teamRepository.findById(teamId).orElseThrow();
//        Member member = memberGetService.getMemberBySocialId(memberId);
//
//        missionArchiveQueryService.findRepeatMissionMyArchive(memberId, );
//    }
//

}
