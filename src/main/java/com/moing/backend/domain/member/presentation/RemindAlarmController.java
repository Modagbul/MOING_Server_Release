package com.moing.backend.domain.member.presentation;

import com.moing.backend.domain.member.application.service.UpdateRemindAlarmUseCase;
import com.moing.backend.domain.member.dto.request.PostUpdatePushAlarm;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/remind/alarm")
public class RemindAlarmController {

    private final UpdateRemindAlarmUseCase updateRemindAlarmUseCase;


    @PostMapping("/update")
    public ResponseEntity<SuccessResponse<Void>> postUpdateRemindAlarm(@AuthenticationPrincipal User user , @RequestBody PostUpdatePushAlarm postUpdatePushAlarm) {
        updateRemindAlarmUseCase.sendUpdateAppPushAlarm(postUpdatePushAlarm.getTitle(), postUpdatePushAlarm.getMessage());
        return ResponseEntity.ok(SuccessResponse.create("remindAlarm Done"));
    }

}
