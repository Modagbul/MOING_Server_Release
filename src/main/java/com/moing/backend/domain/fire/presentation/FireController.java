package com.moing.backend.domain.fire.presentation;

import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.application.service.FireThrowUseCase;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moing.backend.domain.fire.presentation.constant.FireResponseMessage.GET_RECEIVERS_SUCCESS;
import static com.moing.backend.domain.fire.presentation.constant.FireResponseMessage.THROW_FIRE_SUCCESS;
import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.READ_MISSION_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/team/{teamId}/missions/{missionId}/fire")
public class FireController {

    private final FireThrowUseCase fireThrowUseCase;

    /**
     * 불 던지기
     * [POST] {teamId}/mission/{missionId}/fire/{receiveMemberId}
     * 작성자 : 정승연
     */

    @PostMapping("/{receiveMemberId}")
    public ResponseEntity<SuccessResponse<FireThrowRes>> throwFire (@AuthenticationPrincipal User user, @PathVariable("teamId") Long teamId,
                                                                    @PathVariable("receiveMemberId") Long receiveMemberId) {
        return ResponseEntity.ok(SuccessResponse.create(THROW_FIRE_SUCCESS.getMessage(), this.fireThrowUseCase.createFireThrow(user.getSocialId(),receiveMemberId)));
    }

    /**
     * 불 던질 사람 조회 (receivers)
     * [GET] {teamId}/mission/{missionId}/fire
     * 작성자 : 정승연
     */

    @GetMapping()
    public ResponseEntity<SuccessResponse<List<FireReceiveRes>>> throwFireList (@AuthenticationPrincipal User user, @PathVariable("teamId") Long teamId,
                                                                                @PathVariable("missionId") Long missionId) {
        return ResponseEntity.ok(SuccessResponse.create(GET_RECEIVERS_SUCCESS.getMessage(), this.fireThrowUseCase.getFireReceiveList(user.getSocialId(),teamId,missionId)));
    }





}
