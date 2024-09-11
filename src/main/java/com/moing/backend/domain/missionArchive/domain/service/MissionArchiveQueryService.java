package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchivePhotoRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MyArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.domain.missionArchive.exception.NotFoundMissionArchiveException;
import com.moing.backend.domain.missionArchive.domain.repository.OrderCondition;
import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionArchiveQueryService {

    private final MissionRepository missionRepository;
    private final MissionArchiveRepository missionArchiveRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;

    public MissionArchive findByMissionArchiveId(Long missionArchiveId) {
        return missionArchiveRepository.findById(missionArchiveId).orElseThrow(NotFoundMissionArchiveException::new);
    }


    public List<MissionArchive> findMyArchive(Long memberId, Long missionId) {

        Optional<List<MissionArchive>> optional = missionArchiveRepository.findMyArchives(memberId, missionId);

        if (optional.isPresent() && optional.get().size() == 0) {
            return new ArrayList<>();
        } else {
            return optional.get();
        }
    }
    public MissionArchive findOneMyArchive(Long memberId, Long missionId, Long count) {

        List<MissionArchive> missionArchives = missionArchiveRepository.findMyArchives(memberId, missionId).orElseThrow(NotFoundMissionArchiveException::new);
        return missionArchives.stream().filter( m -> m.getCount().equals(count)).findFirst().orElseThrow(NotFoundMissionArchiveException::new);

    }

    public List<MissionArchive> findOthersArchive(Long memberId, Long missionId) {
        return missionArchiveRepository.findOthersArchives(memberId, missionId).orElseThrow(NotFoundMissionArchiveException::new);
    }


    public Boolean isDone(Long memberId, Long missionId) {
        Optional<List<MissionArchive>> byMemberId = missionArchiveRepository.findArchivesByMissionIdAndMemberId(memberId, missionId);
        if (byMemberId.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * mission.getTeam() 팀의 단일미션 미션 인증 보드
     */
    public List<SingleMissionBoardRes> findMySingleMissionArchives(Long memberId, Long teamId, MissionStatus missionStatus) {

        // INCOMPLETE
        List<SingleMissionBoardRes> incompleteList = missionArchiveRepository.findSingleMissionInComplete(memberId, teamId, missionStatus, OrderCondition.DUETO)
                .orElseThrow(NotFoundMissionArchiveException::new);

        List<SingleMissionBoardRes> completeList = missionArchiveRepository.findSingleMissionComplete(memberId, teamId, missionStatus, OrderCondition.CREATED)
                .orElseThrow(NotFoundMissionArchiveException::new);

        incompleteList.addAll(completeList);
        return incompleteList;
    }

    public List<RepeatMissionBoardRes> findMyRepeatMissionArchives(Long memberId, Long teamId, MissionStatus missionStatus) {
        return missionArchiveRepository.findRepeatMissionArchivesByMemberId(memberId, teamId, missionStatus).orElseThrow(NotFoundMissionArchiveException::new);
    }


    public Long findDoneSingleArchives(Long missionId) {
        return missionArchiveRepository.findDonePeopleBySingleMissionId(missionId).orElseThrow(NotFoundMissionArchiveException::new);
    }
    public Long findDoneRepeatArchives(Long missionId) {
        return missionArchiveRepository.findDonePeopleByRepeatMissionId(missionId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public Long findMyDoneArchives(Long memberId, Long missionId) {
        return missionArchiveRepository.findMyDoneCountByMissionId(missionId, memberId).orElseThrow(NotFoundMissionArchiveException::new);
    }


    public List<FinishMissionBoardRes> findMyFinishMissions(Long memberId, Long teamId) {
        return missionArchiveRepository.findFinishMissionsByStatus(memberId, teamId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public List<MissionArchivePhotoRes> findTop5ArchivesByTeam(List<Long> teamIds) {
        return missionArchiveRepository.findTop5ArchivesByTeam(teamIds).orElse(null);
    }

    public boolean isAbleToArchiveToday(Long memberId, Long missionId) {
        return missionArchiveRepository.findMyArchivesToday(memberId, missionId);
    }

    public MyArchiveStatus findMissionStatusById(Long memberId, Long missionId, Long teamId) {
        return missionArchiveRepository.findMissionStatusById(memberId, missionId, teamId);
    }

    public Long stateCountByMissionId(Long missionId) {
        return missionArchiveRepository.getCountsByMissionId(missionId);
    }

    public Long getTodayMissionArchives(){
        return missionArchiveRepository.getTodayMissionArchives();
    }
    public Long getYesterdayMissionArchives(){
        return missionArchiveRepository.getYesterdayMissionArchives();
    }
}
