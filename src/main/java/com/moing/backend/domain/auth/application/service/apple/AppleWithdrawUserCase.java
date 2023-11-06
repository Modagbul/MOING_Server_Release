package com.moing.backend.domain.auth.application.service.apple;

import com.moing.backend.domain.auth.application.service.WithdrawProvider;
import com.moing.backend.domain.auth.application.service.apple.utils.AppleClient;
import com.moing.backend.domain.auth.application.service.apple.utils.AppleToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("appleWithdraw")
@RequiredArgsConstructor
public class AppleWithdrawUserCase implements WithdrawProvider {

    @Value("${oauth2.apple.keyId}")
    private String keyId;

    @Value("${oauth2.apple.teamId}")
    private String teamId;

    @Value("${oauth2.apple.clientId}")
    private String clientId;

    @Value("${oauth2.apple.keyPath}")
    private String keyPath;

    private final AppleClient appleClient;

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
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private Key getPrivateKey() throws IOException {
        ClassPathResource resource = new ClassPathResource(keyPath);
        String privateKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));

        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
    }
}
