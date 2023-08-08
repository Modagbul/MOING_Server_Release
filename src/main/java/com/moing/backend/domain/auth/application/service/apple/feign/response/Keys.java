package com.moing.backend.domain.auth.application.service.apple.feign.response;

import lombok.Data;

import java.util.List;

@Data
public class Keys {

    private List<PubKey> keys;

    @Data
    public static class PubKey{
        private String alg;

        private String e;

        private String kid;

        private String kty;

        private String n;

        private String use;
    }
}

