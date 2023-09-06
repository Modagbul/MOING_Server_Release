package com.moing.backend.domain.missionArchive.presentation;

import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import com.moing.backend.domain.mission.application.service.MissionDeleteUseCase;
import com.moing.backend.domain.mission.application.service.MissionReadUseCase;
import com.moing.backend.domain.mission.application.service.MissionUpdateUseCase;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchive;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveCreateUseCase;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveReadUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.READ_MISSION_SUCCESS;
import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/{missionId}/archive")
public class MissionArchiveController {

    private final MissionArchiveCreateUseCase missionArchiveCreateUseCase;
    private final MissionArchiveReadUseCase missionArchiveReadUseCase;

    /**
     * 미션 인증 하기
     * [POST] {teamId}/{missionId}/missionArchive
     * 작성자 : 정승연
     */

    @PostMapping()
    public ResponseEntity<SuccessResponse<MissionArchiveRes>> createArchive(@AuthenticationPrincipal User user,
                                                                            @PathVariable("teamId") Long teamId,
                                                                            @PathVariable("missionId") Long missionId,
                                                                            @RequestBody MissionArchiveReq missionArchiveReq) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveCreateUseCase.createArchive(user.getSocialId(), missionId,missionArchiveReq)));
    }

    /**
     * 미션 인증 조회
     * [GET] {teamId}/{missionId}/archiveㅡ
     * 작성자 : 정승연
     */

    @GetMapping()
    public ResponseEntity<SuccessResponse<MissionArchiveRes>> getArchives(@AuthenticationPrincipal User user,
                                                                            @PathVariable("teamId") Long teamId,
                                                                            @PathVariable("missionId") Long missionId,
                                                                            @RequestBody MissionArchiveReq missionArchiveReq) {
        return ResponseEntity.ok(SuccessResponse.create(READ_MY_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveReadUseCase.getMyArchive(user.getSocialId(), missionId)));
    }

    /**
     * 모임원 미션 인증 목록 조회
     * [GET] {teamId}/{missionId}/archive/done
     * 작성자 : 정승연
     */
    @GetMapping("/done")
    public ResponseEntity<SuccessResponse<List<PersonalArchive>>> getDoneArchives(@AuthenticationPrincipal User user,
                                                                                  @PathVariable("teamId") Long teamId,
                                                                                  @PathVariable("missionId") Long missionId,
                                                                                  @RequestBody MissionArchiveReq missionArchiveReq) {
        return ResponseEntity.ok(SuccessResponse.create(READ_TEAM_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveReadUseCase.getPersonalArchive(user.getSocialId(), missionId)));
    }

}