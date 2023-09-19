package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.domain.missionArchive.exception.NotFoundMissionArchiveException;
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

    public List<MissionArchive> findMyArchive(Long memberId, Long missionId) {
        return missionArchiveRepository.findByMissionIdAndMemberId(memberId, missionId).orElseThrow();
    }

    public MissionArchive findByMemberId(Long memberId) {
        return missionArchiveRepository.findByMemberId(memberId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public Boolean isDone(Long memberId, Long missionId) {
        Optional<List<MissionArchive>> byMemberId = missionArchiveRepository.findByMissionIdAndMemberId(memberId, missionId);
        if (byMemberId.isPresent()) {
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }

    // team의 mission id 들 가져와서 나의 mission archive 리턴
    public List<MissionArchive> findMySingleMissionArchives(Long memberId, List<Long> missionIds, MissionStatus missionStatus) {
        // INCOMPLETE
        List<MissionArchive> incompleteList = missionArchiveRepository.findSingleMissionArchivesByMemberId(memberId, missionIds, missionStatus.name(), INCOMPLETE.name())
                .orElseThrow(NotFoundMissionArchiveException::new);
        Collections.sort(incompleteList, new Comparator<MissionArchive>() {
            @Override
            public int compare(MissionArchive missionArchive1, MissionArchive missionArchive2) {
                // missionArchive1의 dueTo와 missionArchive2의 dueTo를 비교하여 오름차순으로 정렬
                return missionArchive1.getMission().getDueTo().compareTo(missionArchive2.getMission().getDueTo());
            }
        });

        List<MissionArchive> completeList = missionArchiveRepository.findSingleMissionArchivesByMemberId(memberId, missionIds, missionStatus.name(), COMPLETE.name())
                .orElseThrow(NotFoundMissionArchiveException::new);
        Collections.sort(completeList, new Comparator<MissionArchive>() {
            @Override
            public int compare(MissionArchive missionArchive1, MissionArchive missionArchive2) {
                // missionArchive1의 dueTo와 missionArchive2의 dueTo를 비교하여 오름차순으로 정렬
                return missionArchive2.getMission().getDueTo().compareTo(missionArchive1.getMission().getDueTo());
            }
        });
        incompleteList.addAll(completeList);
        return incompleteList;
    }
    public List<MissionArchive> findMyRepeatMissionArchives(Long memberId, List<Long> missionIds) {
        return missionArchiveRepository.findRepeatMissionArchivesByMission(memberId, missionIds).orElseThrow(NotFoundMissionArchiveException::new);
    }




}
