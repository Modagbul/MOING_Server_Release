package com.moing.backend.domain.auth.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoUserResponse {
    private Long id;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Properties {

        private String nickname;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KakaoAccount {

        private String email;
        private String gender;
        private String ageRange;
    }
    public void adaptResponse() {
        if(kakaoAccount.gender == null || kakaoAccount.gender.isBlank()) kakaoAccount.gender = "undef";
        if(kakaoAccount.ageRange == null || kakaoAccount.ageRange.isBlank()) kakaoAccount.ageRange = "undef";
        if(properties.nickname==null||properties.nickname.isBlank()) properties.nickname="undef";
        if(kakaoAccount.email.length() > 50) kakaoAccount.email = kakaoAccount.email.substring(0, 50);
        if(properties.nickname.length() > 7) properties.nickname = properties.nickname.substring(0, 7);
    }
}
