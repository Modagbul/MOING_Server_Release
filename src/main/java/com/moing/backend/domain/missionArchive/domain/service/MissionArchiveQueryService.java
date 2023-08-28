package com.moing.backend.domain.missionArchive.domain.service;

import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.repository.MissionArchiveRepository;
import com.moing.backend.domain.missionArchive.exception.NotFoundMissionArchiveException;
import com.moing.backend.global.annotation.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class MissionArchiveQueryService {

    private final MissionRepository missionRepository;
    private final MissionArchiveRepository missionArchiveRepository;

//    public List<MissionArchive> findByMissionId(Long missionId) {
//
//    }

    public MissionArchive findByMemberId(Long memberId) {
        return missionArchiveRepository.findByMemberId(memberId).orElseThrow(NotFoundMissionArchiveException::new);
    }

    public Boolean isDone(Long memberId, Long missionId) {
        Optional<MissionArchive> byMemberId = missionArchiveRepository.findByMissionIdAndMemberId(memberId, missionId);
        if (byMemberId.isPresent()) {
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }
}
