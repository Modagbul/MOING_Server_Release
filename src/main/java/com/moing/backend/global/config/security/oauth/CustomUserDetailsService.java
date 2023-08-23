package com.moing.backend.global.config.security.oauth;

import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberGetService memberQueryService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
        Member member = this.memberQueryService.getMemberBySocialId(socialId);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with socialId: " + socialId);
        }

        return new CustomUserDetails(member);
    }
}

