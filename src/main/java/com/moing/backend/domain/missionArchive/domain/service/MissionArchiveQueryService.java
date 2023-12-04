package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.GatherSingleMissionRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchivePhotoRes;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.domain.missionArchive.exception.NotFoundMissionArchiveException;
import com.moing.backend.domain.missionArchive.domain.repository.OrderCondition;
import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus.*;
import static com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus.INCOMPLETE;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionArchiveQueryService {

    private final MissionRepository missionRepository;
    private final MissionArchiveRepository missionArchiveRepository;
    private final TeamMemberRepository teamMemberRepository;

    public MissionArchive findByMissionArchiveId(Long missionArchiveId) {
        return missionArchiveRepository.findById(missionArchiveId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public MissionArchive findArchive(Long memberId, Long missionId) {
        return missionArchiveRepository.findByMissionIdAndMemberId(memberId, missionId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public List<MissionArchive> findMyArchive(Long memberId, Long missionId) {

        Optional<List<MissionArchive>> optional = missionArchiveRepository.findMyArchives(memberId, missionId);

        if (optional.isPresent() && optional.get().size() == 0) {
            return new ArrayList<>();
        } else {
            return optional.get();
        }
    }
    public List<MissionArchive> findOneMyArchive(Long memberId, Long missionId, Long count) {

        Optional<List<MissionArchive>> optional = missionArchiveRepository.findMyArchives(memberId, missionId);

        if (optional.isPresent() && optional.get().size() == 0) {
            return new ArrayList<>();
        } else {
            return optional.get();
        }
    }

    public List<MissionArchive> findOthersArchive(Long memberId, Long missionId) {
        return missionArchiveRepository.findOthersArchives(memberId, missionId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public List<MissionArchive> findArchivesByMemberId(Long memberId) {
        return missionArchiveRepository.findByMemberId(memberId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public Boolean isDone(Long memberId, Long missionId) {
        Optional<List<MissionArchive>> byMemberId = missionArchiveRepository.findArchivesByMissionIdAndMemberId(memberId, missionId);
        if (byMemberId.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Boolean isTodayDone(Long memberId, Long missionId) {
        Optional<List<MissionArchive>> byMemberId = missionArchiveRepository.findArchivesByMissionIdAndMemberId(memberId, missionId);
        if (byMemberId.isPresent()) {

            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // team의 mission id 들 가져와서 나의 mission archive 리턴

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


    public Long findDoneArchives(Long missionId) {
        return missionArchiveRepository.findDonePeopleByMissionId(missionId).orElseThrow(NotFoundMissionArchiveException::new);
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

    public boolean findDoneTodayArchive(Long memberId, Long missionId) {
        return missionArchiveRepository.findMyArchivesToday(memberId, missionId);
    }



}
