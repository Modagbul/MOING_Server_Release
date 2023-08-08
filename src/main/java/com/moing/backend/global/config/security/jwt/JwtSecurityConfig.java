package com.moing.backend.global.config.security.jwt;

import com.moing.backend.domain.member.domain.service.MemberQueryService;
import com.moing.backend.global.config.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenUtil tokenUtil;

    private final MemberQueryService memberQueryService;

    @Override
    public void configure(HttpSecurity http) {
        JwtAuthFilter customFilter = new JwtAuthFilter(tokenUtil, memberQueryService);
        //UsernamePasswordAuthenticationFilter 앞에 필터로 JwtFilter 추가
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
