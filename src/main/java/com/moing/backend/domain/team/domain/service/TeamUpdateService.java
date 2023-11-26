package com.moing.backend.domain.team.domain.service;

import com.moing.backend.domain.team.domain.repository.TeamRepository;
import com.moing.backend.global.annotation.DomainService;
import com.moing.backend.global.config.fcm.dto.event.FcmEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class TeamUpdateService {

    private final TeamRepository teamRepository;
    private final ApplicationEventPublisher eventPublisher;
    public void updateTeamStatus(boolean isApproved, List<Long> teamIds){
        teamRepository.updateTeamStatus(isApproved, teamIds);
    }
}
