package com.moing.backend.domain.auth.application.service;

import com.moing.backend.domain.auth.application.dto.response.CheckNicknameResponse;
import com.moing.backend.domain.member.domain.service.MemberCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckNicknameUserCase {

    private final MemberCheckService memberCheckService;
    public CheckNicknameResponse checkNickname(String nickname){
        boolean isDuplicated=memberCheckService.checkNickname(nickname);
        return new CheckNicknameResponse(isDuplicated);
    }
}
