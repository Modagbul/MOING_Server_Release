package com.moing.backend.domain.auth.application.service;

import com.moing.backend.domain.member.domain.entity.Member;

public interface SignInProvider {
    Member getUserData(String accessToken);
}
