package com.moing.backend.domain.auth.application.service.apple;

import com.moing.backend.domain.auth.application.service.apple.utils.AppleClient;
import com.moing.backend.domain.auth.application.service.apple.utils.Keys;
import com.moing.backend.domain.auth.exception.TokenInvalidException;
import com.moing.backend.global.exception.InternalServerErrorException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AppleTokenUserCase {

    @Value("${oauth2.apple.clientId}")
    private String appId;

    private final AppleClient appleClient;

    public Jws<Claims> sigVerificationAndGetJws(String unverifiedToken) {

        try {
            String kid = getKidFromUnsignedTokenHeader(
                    unverifiedToken,
                    "https://appleid.apple.com",
                    appId);

            Keys keys = appleClient.getKeys();
            Keys.PubKey pubKey = keys.getKeys().stream()
                    .filter((key) -> key.getKid().equals(kid))
                    .findAny()
                    .orElseThrow(TokenInvalidException::new);

            return getOIDCTokenJws(unverifiedToken, pubKey.getN(), pubKey.getE());
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }


    private Jws<Claims> getOIDCTokenJws(String token, String modulus, String exponent) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getRSAPublicKey(modulus, exponent))
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

    private Key getRSAPublicKey(String modulus, String exponent) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
            byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
            BigInteger n = new BigInteger(1, decodeN);
            BigInteger e = new BigInteger(1, decodeE);

            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalServerErrorException("Key creation failed");
        }
    }

    private String getKidFromUnsignedTokenHeader(String token, String iss, String aud) {
        return (String) getUnsignedTokenClaims(token, iss, aud).getHeader().get("kid");
    }

    private Jwt<Header, Claims> getUnsignedTokenClaims(String token, String iss, String aud) {
        try {
            return Jwts.parserBuilder()
                    .requireAudience(aud)
                    .requireIssuer(iss)
                    .build()
                    .parseClaimsJwt(getUnsignedToken(token));
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

    private String getUnsignedToken(String token) {
        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) throw new TokenInvalidException();
        return splitToken[0] + "." + splitToken[1] + ".";
    }
}
