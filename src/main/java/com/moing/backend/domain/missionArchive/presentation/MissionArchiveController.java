package com.moing.backend.domain.missionArchive.presentation;

import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveStatusRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchiveRes;
import com.moing.backend.domain.missionArchive.application.service.*;
import com.moing.backend.domain.missionHeart.application.dto.MissionHeartRes;
import com.moing.backend.domain.missionHeart.application.service.MissionHeartUseCase;
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
@RequestMapping("/api/team/{teamId}/missions/{missionId}/archive")
public class MissionArchiveController {

    private final MissionArchiveCreateUseCase missionArchiveCreateUseCase;
    private final MissionArchiveUpdateUseCase missionArchiveUpdateUseCase;
    private final MissionArchiveReadUseCase missionArchiveReadUseCase;
    private final RepeatMissionArchiveReadUseCase repeatMissionArchiveReadUseCase;
    private final MissionHeartUseCase missionHeartUseCase;


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
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveUpdateUseCase.updateArchive(user.getSocialId(), missionId,missionArchiveReq)));
    }

    /**
     * 미션 인증 조회
     * [GET] {teamId}/missions/{missionId}/archive
     * 작성자 : 정승연
     **/

    @GetMapping()
    public ResponseEntity<SuccessResponse<List<MissionArchiveRes>>> getMyArchive(@AuthenticationPrincipal User user,
                                                                            @PathVariable("teamId") Long teamId,
                                                                            @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(READ_MY_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveReadUseCase.getMyArchive(user.getSocialId(), missionId)));
    }

    /**
     * 모임원 미션 인증 목록 조회
     * [GET] {teamId}/missions/{missionId}/archive/others
     * 작성자 : 정승연
     **/
    @GetMapping("/others")
    public ResponseEntity<SuccessResponse<List<PersonalArchiveRes>>> getOtherPeopleArchives(@AuthenticationPrincipal User user,
                                                                                            @PathVariable("teamId") Long teamId,
                                                                                            @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(READ_TEAM_ARCHIVE_SUCCESS.getMessage(), this.missionArchiveReadUseCase.getPersonalArchive(user.getSocialId(),missionId)));
    }


    /**
     * 미션 인증 좋아요 누르기
     * [POST] {teamId}/missions/{missionId}/archive
     * 작성자 : 정승연
     **/

    @PutMapping("{archiveId}/heart/{missionHeartStatus}")
    public ResponseEntity<SuccessResponse<MissionHeartRes>> pushHeart(@AuthenticationPrincipal User user,
                                                                      @PathVariable("teamId") Long teamId,
                                                                      @PathVariable("missionId") Long missionId,
                                                                      @PathVariable("archiveId") Long archiveId,
                                                                      @PathVariable("missionHeartStatus") String missionHeartStatus) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_ARCHIVE_SUCCESS.getMessage(), this.missionHeartUseCase.pushHeart(user.getSocialId(),archiveId,missionHeartStatus)));
    }

    /**
     * 인증 성공 인원 조회
     * [GET] {teamId}/missions/{missionId}/archive/status
     * 작성자 : 정승연
     **/

    @GetMapping("/status")
    public ResponseEntity<SuccessResponse<MissionArchiveStatusRes>> getMissionDoneStatus(@AuthenticationPrincipal User user,
                                                                                         @PathVariable("teamId") Long teamId,
                                                                                         @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(MISSION_ARCHIVE_PEOPLE_STATUS_SUCCESS.getMessage(), this.missionArchiveReadUseCase.getMissionDoneStatus(missionId)));
    }



    /**
     * 반복미션 - 나의 성공 횟수 조회
     * [GET] {teamId}/missions/{missionId}/archive/my-status
     * 작성자 : 정승연
     **/

    @GetMapping("/my-status")
    public ResponseEntity<SuccessResponse<MissionArchiveStatusRes>> getMyMissionDoneStatus(@AuthenticationPrincipal User user,
                                                                                         @PathVariable("teamId") Long teamId,
                                                                                         @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(MISSION_ARCHIVE_PEOPLE_STATUS_SUCCESS.getMessage(), this.repeatMissionArchiveReadUseCase.getMyMissionDoneStatus(user.getSocialId(),missionId)));
    }




}