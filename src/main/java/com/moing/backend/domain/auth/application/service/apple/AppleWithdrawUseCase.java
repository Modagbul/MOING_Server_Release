package com.moing.backend.domain.auth.application.service.apple;

import com.moing.backend.domain.auth.application.service.WithdrawProvider;
import com.moing.backend.domain.auth.application.service.apple.utils.AppleClient;
import com.moing.backend.domain.auth.application.service.apple.utils.AppleToken;
import com.moing.backend.global.config.sns.AppleConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("appleWithdraw")
@RequiredArgsConstructor
public class AppleWithdrawUseCase implements WithdrawProvider {

    @Value("${oauth2.apple.keyId}")
    private String keyId;

    @Value("${oauth2.apple.teamId}")
    private String teamId;

    @Value("${oauth2.apple.clientId}")
    private String clientId;

    private final AppleClient appleClient;
    private final AppleConfig appleConfig;

    public void withdraw(String token) throws IOException {
        AppleToken.Response response = generateAuthToken(token);

        if (response.getAccess_token() != null) {
            appleClient.revoke(AppleToken.RevokeRequest.of(
                            clientId,
                            createClientSecret(),
                            response.getAccess_token()
                    )
            );
        }
    }

    public AppleToken.Response generateAuthToken(String authorizationCode) throws IOException {

        return appleClient.getToken(AppleToken.Request.of(
                authorizationCode,
                clientId,
                createClientSecret(),
                "authorization_code"
        ));
    }

    public String createClientSecret() throws IOException {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", keyId);
        jwtHeader.put("alg", "ES256");

        return Jwts.builder()
                .setHeaderParams(jwtHeader)
                .setIssuer(teamId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(clientId)
                .signWith(appleConfig.applePrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }
}
