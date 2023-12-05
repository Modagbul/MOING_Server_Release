package com.moing.backend.domain.auth.application.service.kakao;

import com.moing.backend.domain.auth.application.service.WithdrawProvider;
import com.moing.backend.domain.auth.application.service.kakao.utils.KakaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("kakaoWithdraw")
@RequiredArgsConstructor
public class KakaoWithdrawUseCase implements WithdrawProvider {

    private final KakaoClient kakaoClient;

    public void withdraw(String token) throws IOException {

        kakaoClient.unlinkUser("Bearer " + token);
    }
}
