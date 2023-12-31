package com.moing.backend.domain.missionArchive.application.service;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 모아보기
@Service
@Transactional
@RequiredArgsConstructor
public class MissionArchiveBoardUseCase {

    private final MissionArchiveQueryService missionArchiveQueryService;
    private final MissionQueryService missionQueryService;

    private final MemberGetService memberGetService;
    private final TeamRepository teamRepository;

    /*
     * 팀별 미션 보드 중 진행중인 단일 미션(한 번 인증)
     */
    public List<SingleMissionBoardRes> getActiveSingleMissions(Long teamId, String memberId) {

        Member member = memberGetService.getMemberBySocialId(memberId);

        return missionArchiveQueryService.findMySingleMissionArchives(member.getMemberId(), teamId, MissionStatus.ONGOING);

    }

    /*
     * 팀별 미션 보드 중 종료한 전체 미션
     */
    public List<FinishMissionBoardRes> getFinishMissions(Long teamId, String memberId) {

        Team team = teamRepository.findById(teamId).orElseThrow();
        Member member = memberGetService.getMemberBySocialId(memberId);

        return missionArchiveQueryService.findMyFinishMissions(member.getMemberId(), teamId);

    }

    public List<RepeatMissionBoardRes> getActiveRepeatMissions(Long teamId, String memberId) {

        Member member = memberGetService.getMemberBySocialId(memberId);

        List<RepeatMissionBoardRes> myRepeatMissionArchives = missionArchiveQueryService.findMyRepeatMissionArchives(member.getMemberId(), teamId, MissionStatus.ONGOING);


        LocalDate currentDate = LocalDate.now();
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
        if (currentDayOfWeek == DayOfWeek.SUNDAY) {
            myRepeatMissionArchives.stream().forEach(
                    repeatMissionBoardRes -> repeatMissionBoardRes.setDueTo("True")
            );
        }

        return myRepeatMissionArchives;

    }




}
