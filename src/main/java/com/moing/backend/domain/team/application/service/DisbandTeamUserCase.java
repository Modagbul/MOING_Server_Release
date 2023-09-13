package com.moing.backend.domain.team.application.service;

import com.moing.backend.domain.team.application.dto.response.DeleteTeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DisbandTeamUserCase {

    public DeleteTeamResponse disbandTeam(String socialId, Long teamId){
        // 1. 소모임장인지 확인 -> 아니면 오류
        // 2. 소모임 강제 종료
        return null;
    }
}
