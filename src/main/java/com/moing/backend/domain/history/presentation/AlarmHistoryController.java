package com.moing.backend.domain.history.presentation;

import com.moing.backend.domain.history.application.dto.response.GetAlarmHistoryResponse;
import com.moing.backend.domain.history.application.service.GetAlarmHistoryUseCase;
import com.moing.backend.domain.history.application.service.ReadAlarmHistoryUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.moing.backend.domain.history.presentation.constant.AlarmHistoryResponseMessage.GET_ALL_ALARM_HISTORY;
import static com.moing.backend.domain.history.presentation.constant.AlarmHistoryResponseMessage.READ_ALARM_HISTORY;


@RestController
@AllArgsConstructor
@RequestMapping("/api/history/alarm")
public class AlarmHistoryController {

    private final GetAlarmHistoryUseCase getAlarmHistoryUseCase;
    private final ReadAlarmHistoryUseCase readAlarmHistoryUseCase;
    /**
     * 알림 전체 조회
     * [GET] api/history/alarm
     * 작성자 : 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<List<GetAlarmHistoryResponse>>> getAllAlarmHistories(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(SuccessResponse.create(GET_ALL_ALARM_HISTORY.getMessage(), getAlarmHistoryUseCase.getAllAlarmHistories(user.getSocialId())));
    }

    /**
     * 알림 한개 조회
     * [POST] api/history/alarm/read?alarmHistoryId=1
     */
    @PostMapping("/read")
    public ResponseEntity<SuccessResponse> readAlarmHistory(@AuthenticationPrincipal User user,
                                                            @RequestParam Long alarmHistoryId) {
        readAlarmHistoryUseCase.readAlarmHistory(user.getSocialId(), alarmHistoryId);
        return ResponseEntity.ok(SuccessResponse.create(READ_ALARM_HISTORY.getMessage()));
    }

    /**
     * 안 읽은 알림 개수 조회
     * [GET] api/history/alarm/count
     */
    @GetMapping("/count")
    public ResponseEntity<SuccessResponse<>> getAlarmHistoryCount(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(SuccessResponse.create())
    }
}
