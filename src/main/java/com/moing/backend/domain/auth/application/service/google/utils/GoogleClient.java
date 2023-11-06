package com.moing.backend.domain.auth.application.service.google.utils;

import com.moing.backend.global.util.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleClient", url = "https://oauth2.googleapis.com", configuration = FeignClientConfig.class)
public interface GoogleClient {
    @PostMapping(value = "/revoke", consumes = "application/x-www-form-urlencoded")
    void revoke(@RequestParam("token") String token);
}
