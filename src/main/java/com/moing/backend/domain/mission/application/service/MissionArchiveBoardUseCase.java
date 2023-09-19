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
    public List<SingleMissionBoardRes> getActiveSingleMissions(Long teamId, String memberId,Long missionId) {

        Team team = teamRepository.findById(teamId).orElseThrow();
        Member member = memberGetService.getMemberBySocialId(memberId);

        // 인증 상태 가져올 미션들
        List<Long> missionIds = new ArrayList<>();
        team.getMissions().forEach(mission -> missionIds.add(mission.getId()));

        List<MissionArchive> mySingleMissionArchives = missionArchiveQueryService.findMySingleMissionArchives(member.getMemberId(), missionIds, MissionStatus.ONGOING);
        return MissionArchiveMapper.mapToSingleMissionBoardResList(mySingleMissionArchives);

    }

//    public List<RepeatMissionBoardRes> getActiveRepeatMissions(Long teamId, String memberId) {
//
//        Team team = teamRepository.findById(teamId).orElseThrow();
//        Member member = memberGetService.getMemberBySocialId(memberId);
//
//        missionArchiveQueryService.findRepeatMissionMyArchive(memberId, );
//    }
//
//    public List<FinishMissionBoardRes> getFinishMissions(Long teamId, Long memberId) {
//
//
//
//    }
//    public List<MissionArchiveRes> getFinishRepeatMissions(Long teamId, Long memberId) {
//
//    }
}
