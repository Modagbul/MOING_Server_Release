package com.moing.backend.domain.auth.application.service.apple.utils;

import lombok.Getter;

public class AppleToken {

    public static class Request {
        private String code;
        private String client_id;
        private String client_secret;
        private String grant_type;

        public static Request of(String code, String clientId, String clientSecret, String grantType) {
            Request request = new Request();
            request.code = code;
            request.client_id = clientId;
            request.client_secret = clientSecret;
            request.grant_type = grantType;
            return request;
        }
    }

    @Getter
    public static class Response {
        private String access_token;
        private String expires_in;
        private String id_token;
        private String refresh_token;
        private String token_type;
        private String error;
    }

    @Getter
    public static class RevokeRequest {
        private String client_id;
        private String client_secret;
        private String token;

        public static RevokeRequest of(String clientId, String clientSecret, String token) {
            RevokeRequest request = new RevokeRequest();
            request.client_id = clientId;
            request.client_secret = clientSecret;
            request.token = token;
            return request;
        }
    }
}