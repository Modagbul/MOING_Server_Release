package com.moing.backend.domain.team.presentation;

import com.moing.backend.domain.team.application.service.CreateTeamUserCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/team")
public class AdminTeamController {

    //TODO 소모임 승인, 반려하기

    private final CreateTeamUserCase createTeamService;
}
