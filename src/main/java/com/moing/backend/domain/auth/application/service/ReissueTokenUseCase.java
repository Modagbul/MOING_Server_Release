package com.moing.backend.domain.auth.application.service;

import com.moing.backend.domain.auth.application.dto.response.ReissueTokenResponse;
import com.moing.backend.global.config.security.jwt.NotFoundRefreshToken;
import com.moing.backend.global.config.security.jwt.TokenUtil;
import com.moing.backend.global.response.TokenInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReissueTokenUseCase {

    private final TokenUtil tokenUtil;

    public ReissueTokenResponse reissueToken(String token) {
        // refresh 토큰이 유효한지 확인
        if (token != null && tokenUtil.verifyToken(token)) {
            // 토큰 새로 받아오기
            TokenInfoResponse newToken = tokenUtil.tokenReissue(token);

            return ReissueTokenResponse.from(newToken);
        }
        throw new NotFoundRefreshToken();
    }
}
