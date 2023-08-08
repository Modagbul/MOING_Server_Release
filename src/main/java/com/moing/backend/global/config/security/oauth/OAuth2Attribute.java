package com.moing.backend.global.config.security.oauth;

import com.moing.backend.domain.member.domain.constant.SocialProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
class OAuth2Attribute {
    private Map<String, Object> attributes;

    private String socialId;
    private SocialProvider provider;
    private String name;
    private String nickname;
    private String email;
    private String ageRange;
    private String gender;


    static OAuth2Attribute of(String provider, String attributeKey,
                              Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributeKey, attributes);
            case "kakao":
                return ofKakao("id", attributes);
            case "naver":
                return ofNaver("id", attributes);
            case "apple":
                return ofApple("id", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofGoogle(String attributeKey,
                                            Map<String, Object> attributes) {
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.builder()
                .socialId(SocialProvider.GOOGLE + "@" + attributes.get(attributeKey))
                .provider(SocialProvider.GOOGLE)
                .name((String) attributes.get("name"))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .ageRange("undef")
                .gender("undef")
                .build();

        oAuth2Attribute.adaptGoogleResponse();

        return oAuth2Attribute;
    }

    private static OAuth2Attribute ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.builder()
                .socialId(SocialProvider.APPLE + "@" + attributes.get(attributeKey))
                .provider(SocialProvider.APPLE)
                .name((String) profile.get("nickname"))
                .nickname((String) profile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .gender((String) kakaoAccount.get("gender"))
                .ageRange((String) kakaoAccount.get("age_range"))
                .build();

        oAuth2Attribute.adaptKakaoResponse();

        return oAuth2Attribute;
    }

    private static OAuth2Attribute ofNaver(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        System.out.println(response);

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.builder()
                .socialId(SocialProvider.NAVER + "@" + response.get(attributeKey))
                .provider(SocialProvider.NAVER)
                .name((String) response.get("nickname"))
                .nickname((String) response.get("nickname"))
                .email((String) response.get("email"))
                .gender((String) response.get("gender"))
                .ageRange((String) response.get("age"))
                .build();

        oAuth2Attribute.adaptNaverResponse();

        return oAuth2Attribute;
    }

    private static OAuth2Attribute ofApple(String attributeKey,
                                           Map<String, Object> attributes) {
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.builder()
                .socialId(SocialProvider.APPLE + "@" + attributes.get(attributeKey))
                .provider(SocialProvider.APPLE)
                .name((String) attributes.get("email"))
                .nickname("undef")
                .email((String) attributes.get("email"))
                .gender("undef")
                .ageRange("undef")
                .build();

        oAuth2Attribute.adaptAppleResponse();

        return oAuth2Attribute;
    }

    Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("socialId", socialId);
        map.put("provider", provider);
        map.put("name", name);
        map.put("nickname", nickname);
        map.put("email", email);
        map.put("gender", gender);
        map.put("age", ageRange);

        return map;
    }

    public void adaptNaverResponse() {
        if (gender == null || gender.isBlank()) gender = "undef";
        else if (gender.equals("M")) gender = "male";
        else if (gender.equals("F")) gender = "female";
        else gender = "undef";

        if (ageRange == null || ageRange.isBlank()) ageRange = "undef";
        if (email.length() > 50) email = email.substring(0, 50);
        if (nickname.length() > 7) {
            name = name.substring(0, 7);
            nickname = nickname.substring(0, 7);
        }
    }

    public void adaptKakaoResponse() {
        if (gender == null || gender.isBlank()) gender = "undef";
        if (ageRange == null || ageRange.isBlank()) ageRange = "undef";
        if (email.length() > 50) email = email.substring(0, 50);
        if (nickname.length() > 7) {
            name = name.substring(0, 7);
            nickname = nickname.substring(0, 7);
        }
    }

    public void adaptGoogleResponse() {
        if (email.length() > 50) email = email.substring(0, 50);
        if (name.length() > 7) {
            name = name.substring(0, 7);
            nickname = nickname.substring(0, 7);
        }
    }

    public void adaptAppleResponse() {
        if (email.length() > 50) email = email.substring(0, 50);
        if (name.length() > 7) {
            name = name.substring(0, 7);
            nickname = nickname.substring(0, 7);
        }
    }
}
