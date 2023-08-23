package com.moing.backend.global.config.security.jwt;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.global.config.redis.RedisUtil;
import com.moing.backend.global.response.TokenInfoResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtil implements InitializingBean {

    private static final String ADDITIONAL_INFO = "isAdditionalInfoProvided";
    private final RedisUtil redisUtil;
    private final MemberGetService memberQueryService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-period}")
    private long accessTokenValidityTime;

    @Value("${jwt.refresh-token-period}")
    private long refreshTokenValidityTime;

    @Value("${jwt.reissue-token-period}")
    private Long reissueTokenValidityTime;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * 토큰 만드는 함수
     *
     * @param member
     * @return TokenInfoResponse
     */
    public TokenInfoResponse createToken(Member member, boolean isAdditionalInfoProvided) {
        // claim 생성
        Claims claims = getClaims(member, isAdditionalInfoProvided);

        Date now = new Date();
        Date accessTokenValidity = new Date(now.getTime() + this.accessTokenValidityTime);
        Date refreshTokenValidity = new Date(now.getTime() + this.refreshTokenValidityTime);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessTokenValidity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(refreshTokenValidity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenInfoResponse.from("Bearer", accessToken, refreshToken, refreshTokenValidityTime);
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    //refresh token 관련
    public void storeRefreshToken(String socialId, TokenInfoResponse token) {
        redisUtil.save(token.getRefreshToken(), socialId);
    }

    public TokenInfoResponse tokenReissue(String token) {
        String socialId = getSocialId(token);
        Member member = memberQueryService.getMemberBySocialId(socialId);

        // socialId 에 해당하는 refreshToken redis 에서 가져오기
        String storedRefreshToken = redisUtil.findById(socialId).orElseThrow(() -> new NotFoundRefreshToken());

        // Token 생성
        TokenInfoResponse newToken = createToken(member, true);

        // refreshToken 기간이 얼마남지 않았을 경우 (3일 미만)
        if (isExpired(token)) {
            log.info("Refresh token reissue");
            storeRefreshToken(socialId, newToken);
        }

        // refreshToken 의 유효기간이 3일 이상 남았을 경우 (refreshToken 그대로 넣어서 응답)
        else newToken.updateRefreshToken(token);

        return newToken;
    }


    private boolean isExpired(String token) {
        Date expireDate = getExpiration(token);
        Date currentDate = new Date();

        if (expireDate.getTime() - currentDate.getTime() < reissueTokenValidityTime)
            return true;
        else
            return false;
    }


    //토큰 만료시키기
    public void expireRefreshToken(String socialId) {
        redisUtil.deleteById(socialId);
    }

    // get 함수
    public boolean getAdditionalInfoProvided(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.get(ADDITIONAL_INFO, Boolean.class);
    }

    private static Claims getClaims(Member member, boolean isAdditionalInfoProvided) {
        // claim 에 socialId 정보 추가
        Claims claims = Jwts.claims().setSubject(member.getSocialId());
        claims.put(ADDITIONAL_INFO, isAdditionalInfoProvided);
        return claims;
    }

    private Date getExpiration(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    public String getSocialId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


}

