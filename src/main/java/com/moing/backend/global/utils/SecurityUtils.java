package com.moing.backend.global.utils;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.global.config.security.oauth.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class SecurityUtils {

    public static Member getLoggedInUser() {
        try {
            return
                    ((CustomUserDetails) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getMember();
        } catch (NullPointerException e) {
            throw new RuntimeException();
        }
    }

}
