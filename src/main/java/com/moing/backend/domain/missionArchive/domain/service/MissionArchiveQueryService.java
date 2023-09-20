package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveCustomRepository;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.domain.missionArchive.exception.NotFoundMissionArchiveException;
import com.moing.backend.domain.missionArchive.exception.NotFoundMissionArchiveException;
import com.moing.backend.domain.teamMember.domain.repository.OrderCondition;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus.*;
import static com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus.INCOMPLETE;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionArchiveQueryService {

    private final MissionRepository missionRepository;
    private final MissionArchiveRepository missionArchiveRepository;

    public MissionArchive findByMissionArchiveId(Long missionArchiveId) {
        return missionArchiveRepository.findById(missionArchiveId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public MissionArchive findArchive(Long memberId, Long missionId) {
        return missionArchiveRepository.findByMissionIdAndMemberId(memberId, missionId).orElseThrow(NotFoundMissionArchiveException::new);
    }
    public List<MissionArchive> findMyArchive(Long memberId, Long missionId) {

        Optional<List<MissionArchive>> optional = missionArchiveRepository.findArchivesByMissionIdAndMemberId(memberId, missionId);
        if (optional.isPresent() && optional.get().size() == 0) {
            throw new NotFoundMissionArchiveException();
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
        }
        else{
            return Boolean.FALSE;
        }
    }

    // team의 mission id 들 가져와서 나의 mission archive 리턴

    /**
     * mission.getTeam() 팀의 단일미션 미션 인증 보드
     */
    public List<MissionArchive> findMySingleMissionArchives(Long memberId, List<Long> missionIds, MissionStatus missionStatus) {
        // INCOMPLETE
        List<MissionArchive> incompleteList = missionArchiveRepository.findSingleMissionArchivesByMemberId(memberId, missionIds, missionStatus.name(), INCOMPLETE.name(), OrderCondition.DUETO)
                .orElseThrow(NotFoundMissionArchiveException::new);

        List<MissionArchive> completeList = missionArchiveRepository.findSingleMissionArchivesByMemberId(memberId, missionIds, missionStatus.name(), COMPLETE.name(),OrderCondition.CREATED)
                .orElseThrow(NotFoundMissionArchiveException::new);

        incompleteList.addAll(completeList);
        return incompleteList;
    }
    public List<MissionArchive> findMyRepeatMissionArchives(Long memberId, List<Long> missionIds) {
        return missionArchiveRepository.findRepeatMissionArchivesByMission(memberId, missionIds).orElseThrow(NotFoundMissionArchiveException::new);
    }




}