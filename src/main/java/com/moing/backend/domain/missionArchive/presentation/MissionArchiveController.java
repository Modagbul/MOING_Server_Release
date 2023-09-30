package com.moing.backend.domain.missionArchive.presentation;

import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveHeartReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveHeartRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveStatusRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchive;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveCreateUseCase;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveHeartUseCase;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveUpdateUseCase;
import com.moing.backend.domain.missionArchive.application.service.SingleMissionArchiveReadUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/missions/{missionId}/archive")
public class MissionArchiveController {

    private final MissionArchiveCreateUseCase missionArchiveCreateUseCase;
    private final MissionArchiveUpdateUseCase missionArchiveUpdateUseCase;
    private final SingleMissionArchiveReadUseCase singleMissionArchiveReadUseCase;
    private final MissionArchiveHeartUseCase missionArchiveHeartUseCase;

    /**
     * 미션 인증 하기
     * [POST] {teamId}/missions/{missionId}/archive
     * 작성자 : 정승연
     **/

    @PostMapping()
    public ResponseEntity<SuccessResponse<MissionArchiveRes>> createArchive(@AuthenticationPrincipal User user,
                                                                            @PathVariable("teamId") Long teamId,
                                                                            @PathVariable("missionId") Long missionId,
                                                                            @RequestBody MissionArchiveReq missionArchiveReq) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveCreateUseCase.createArchive(user.getSocialId(), missionId,missionArchiveReq)));
    }

    /**
     * 미션 재인증 하기
     * [POST] {teamId}/missions/{missionId}/archive
     * 작성자 : 정승연
     **/

    @PutMapping()
    public ResponseEntity<SuccessResponse<MissionArchiveRes>> updateArchive(@AuthenticationPrincipal User user,
                                                                            @PathVariable("teamId") Long teamId,
                                                                            @PathVariable("missionId") Long missionId,
                                                                            @RequestBody MissionArchiveReq missionArchiveReq) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveUpdateUseCase.updateArchive(user.getSocialId(), missionId,missionArchiveReq)));
    }

    /**
     * 미션 인증 조회
     * [GET] {teamId}/missions/{missionId}/archive
     * 작성자 : 정승연
     **/

    @GetMapping()
    public ResponseEntity<SuccessResponse<MissionArchiveRes>> getMyArchive(@AuthenticationPrincipal User user,
                                                                            @PathVariable("teamId") Long teamId,
                                                                            @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(READ_MY_ARCHIVE_SUCCESS.getMessage(), this.singleMissionArchiveReadUseCase.getMyArchive(user.getSocialId(), missionId)));
    }

    /**
     * 모임원 미션 인증 목록 조회
     * [GET] {teamId}/missions/{missionId}/archive/others
     * 작성자 : 정승연
     **/
    @GetMapping("/others")
    public ResponseEntity<SuccessResponse<List<PersonalArchive>>> getOtherPeopleArchives(@AuthenticationPrincipal User user,
                                                                                  @PathVariable("teamId") Long teamId,
                                                                                  @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(READ_TEAM_ARCHIVE_SUCCESS.getMessage(), this.singleMissionArchiveReadUseCase.getPersonalArchive(user.getSocialId(),missionId)));
    }

    /**
     * 인증 성공 인원 조회
     * [GET] {teamId}/m원issions/{missionId}/archive/status
     * 작성자 : 정승연
     **/

    @GetMapping("/status")
    public ResponseEntity<SuccessResponse<MissionArchiveStatusRes>> getMissionDoneStatus(@AuthenticationPrincipal User user,
                                                                                         @PathVariable("teamId") Long teamId,
                                                                                         @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(MISSION_ARCHIVE_PEOPLE_STATUS_SUCCESS.getMessage(), this.singleMissionArchiveReadUseCase.getMissionDoneStatus(missionId)));
    }

    /**
     * 미션 인증 게시물 좋아요
     * [GET] {teamId}/m원issions/{missionId}/archive/hearts
     * 작성자 : 정승연
     **/

    @PostMapping("/hearts")
    public ResponseEntity<SuccessResponse<MissionArchiveHeartRes>> pushHeart(@AuthenticationPrincipal User user,
                                                                             @PathVariable("teamId") Long teamId,
                                                                             @PathVariable("missionId") Long missionId,
                                                                             @RequestBody MissionArchiveHeartReq missionArchiveHeartReq) {
        return ResponseEntity.ok(SuccessResponse.create(HEART_UPDATE_SUCCESS.getMessage(), this.missionArchiveHeartUseCase.pushHeart(missionArchiveHeartReq)));
    }



}