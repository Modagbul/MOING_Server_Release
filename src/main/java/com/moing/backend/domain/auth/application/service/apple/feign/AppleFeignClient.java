package com.moing.backend.domain.auth.application.service.apple.feign;

import com.moing.backend.domain.auth.application.service.apple.feign.response.Keys;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @Cacheable(cacheNames = "appleOICD", cacheManager = "oidcCacheManager")
    @GetMapping(value = "/keys")
    Keys getKeys();

    @GetMapping(value = "/token")
    String getAccessToken();
}
