package com.moing.backend.domain.mission.presentation;

import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.dto.res.MissionConfirmRes;
import com.moing.backend.domain.mission.application.service.*;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/team/{teamId}/missions")
public class MissionController {


    private final MissionCreateUseCase missionCreateUseCase;
    private final MissionReadUseCase missionReadUseCase;
    private final MissionUpdateUseCase missionUpdateUseCase;
    private final MissionDeleteUseCase missionDeleteUseCase;

//    private final MissionRemindAlarmUseCase missionRemindAlarmUseCase;

    /**
     * 미션 조회
     * [GET] {teamId}/missions/{missionId}
     * 작성자 : 정승연
     */

    @GetMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<MissionReadRes>> getMission(@AuthenticationPrincipal User user,@PathVariable("teamId") Long teamId, @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(READ_MISSION_SUCCESS.getMessage(), this.missionReadUseCase.getMission(user.getSocialId(),missionId)));
    }



    /**
     * 미션 생성
     * [POST] {teamId}/missions
     * 작성자 : 정승연
     */

    @PostMapping()
    public ResponseEntity<SuccessResponse<MissionCreateRes>> createMission(@AuthenticationPrincipal User user,@PathVariable("teamId") Long teamId, @RequestBody MissionReq missionReq) {
        return ResponseEntity.ok(SuccessResponse.create(CREATE_MISSION_SUCCESS.getMessage(), this.missionCreateUseCase.createMission(user.getSocialId(),teamId,missionReq)));
    }

    /**
     * 미션 수정
     * [PUT] {teamId}/missions/{missionId}
     * 작성자 : 정승연
     */
    @PutMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<MissionCreateRes>> updateMission(@AuthenticationPrincipal User user,@PathVariable("teamId") Long teamId, @PathVariable("missionId") Long missionId, @RequestBody MissionReq missionReq) {
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_MISSION_SUCCESS.getMessage(), this.missionUpdateUseCase.updateMission(user.getSocialId(),missionId, missionReq)));
    }

    /**
     * 미션 종료
     * [PUT] {teamId}/missions/{missionId}/end
     * 작성자 : 정승연
     */
    @PutMapping("/{missionId}/end")
    public ResponseEntity<SuccessResponse<MissionReadRes>> endMission(@AuthenticationPrincipal User user,@PathVariable("teamId") Long teamId,@PathVariable Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(END_MISSION_SUCCESS.getMessage(), this.missionUpdateUseCase.updateMissionStatus(user.getSocialId(),missionId)));
    }

    /**
     * 미션 삭제
     * [DELETE] {teamId}/missions/{missionId}
     * 작성자 : 정승연
     */
    @DeleteMapping("/{missionId}")
    public ResponseEntity<SuccessResponse<Long>> deleteMission(@AuthenticationPrincipal User user,@PathVariable Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(DELETE_MISSION_SUCCESS.getMessage(), this.missionDeleteUseCase.deleteMission(user.getSocialId(),missionId)));
    }

    /**
     * 미션 추천
     * [GET] {teamId}/missions/recommend
     * 작성자 : 정승연
     */

    @GetMapping("/recommend")
    public ResponseEntity<SuccessResponse<String>> recommendMission(@AuthenticationPrincipal User user,@PathVariable Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(RECOMMEND_MISSION_SUCCESS.getMessage(), this.missionReadUseCase.getTeamCategory(teamId)));
    }
    /**
     * 미션 추천
     * [GET] {teamId}/missions/isLeader
     * 작성자 : 정승연
     */

    @GetMapping("/isLeader")
    public ResponseEntity<SuccessResponse<Boolean>> isLeader(@AuthenticationPrincipal User user,@PathVariable Long teamId) {
        return ResponseEntity.ok(SuccessResponse.create(RECOMMEND_MISSION_SUCCESS.getMessage(), this.missionCreateUseCase.getIsLeader(user.getSocialId(),teamId)));
    }

//    @PostMapping("/remind")
//    public ResponseEntity<SuccessResponse<Boolean>> remindAlarm(@AuthenticationPrincipal User user,@PathVariable Long teamId) {
//        return ResponseEntity.ok(SuccessResponse.create(RECOMMEND_MISSION_SUCCESS.getMessage(), this.missionRemindAlarmUseCase.sendRepeatMissionRemind()));
//    }

    /**
     * 미션 설명 확인 (미션 읽음 처리)
     * [GET] {teamId}/missions/{missionId}/read
     * 작성자 : 김민수
     */
    @GetMapping("/{missionId}/confirm")
    public ResponseEntity<SuccessResponse<MissionConfirmRes>> confirmMissionExplanation(@AuthenticationPrincipal User user,
                                                                              @PathVariable Long teamId,
                                                                              @PathVariable Long missionId){
        return ResponseEntity.ok(SuccessResponse.create(CONFIRM_MISSION_SUCCESS.getMessage(), this.missionReadUseCase.confirmMission(user.getSocialId(), missionId, teamId)));

    }




}
