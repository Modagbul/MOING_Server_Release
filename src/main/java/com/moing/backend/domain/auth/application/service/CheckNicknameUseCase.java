package com.moing.backend.domain.auth.application.service;

import com.moing.backend.domain.auth.application.dto.response.CheckNicknameResponse;
import com.moing.backend.domain.member.domain.service.MemberCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckNicknameUseCase {

    private final MemberCheckService memberCheckService;

    @Transactional(readOnly=true)
    public CheckNicknameResponse checkNickname(String nickname){
        boolean isDuplicated=memberCheckService.checkNickname(nickname);
        return new CheckNicknameResponse(isDuplicated);
    }
}
