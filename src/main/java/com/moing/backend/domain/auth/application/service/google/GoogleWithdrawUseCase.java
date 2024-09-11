package com.moing.backend.domain.auth.application.service.google;

import com.moing.backend.domain.auth.application.service.WithdrawProvider;
import com.moing.backend.domain.auth.application.service.google.utils.GoogleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("googleWithdraw")
@RequiredArgsConstructor
public class GoogleWithdrawUseCase implements WithdrawProvider {

    private final GoogleClient googleClient;

    public void withdraw(String token) throws IOException {
        googleClient.revoke(token);
    }

}
