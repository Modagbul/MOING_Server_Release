package com.moing.backend.domain.auth.application.service.kakao.utils;

import com.moing.backend.global.util.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoClient", url = "https://kapi.kakao.com", configuration = FeignClientConfig.class)
public interface KakaoClient {
    @PostMapping("/v1/user/unlink")
    KakaoUnlinkResponse unlinkUser(@RequestHeader("Authorization") String accessToken);
}
