package com.moing.backend.domain.mypage.presentation;

import com.moing.backend.domain.mypage.application.dto.request.UpdateProfileRequest;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.application.dto.response.GetAlarmResponse;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageResponse;
import com.moing.backend.domain.mypage.application.dto.response.GetProfileResponse;
import com.moing.backend.domain.mypage.application.service.*;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.mypage.presentation.constant.MypageResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final SignOutUserCase signOutService;
    private final WithdrawUserCase withdrawService;
    private final ProfileUserCase profileUserCase;
    private final AlarmUserCase alarmUserCase;
    private final GetMyPageUserCase getMyPageUserCase;

    /**
     * 로그아웃
     * [POST] api/mypage/signOut
     * 작성자 : 김민수
     */
    @PostMapping("/signOut")
    public ResponseEntity<SuccessResponse> signOut(@AuthenticationPrincipal User user) {
        this.signOutService.signOut(user.getSocialId());
        return ResponseEntity.ok(SuccessResponse.create(SIGN_OUT_SUCCESS.getMessage()));
    }

    /**
     * 회원탈퇴
     * [DELETE] api/mypage/withdrawal
     * 작성자 : 김민수
     */
    @DeleteMapping("/withdrawal")
    public ResponseEntity<SuccessResponse> withdraw(@AuthenticationPrincipal User user,
                                                    @Valid @RequestBody WithdrawRequest withdrawRequest) {
        this.withdrawService.withdraw(user.getSocialId(), withdrawRequest);
        return ResponseEntity.ok(SuccessResponse.create(WITHDRAWAL_SUCCESS.getMessage()));
    }

    /**
     * 마이페이지 조회
     * [GET] api/mypage
     * 작성자: 김민수
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<GetMyPageResponse>> getMyPage(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(SuccessResponse.create(GET_MYPAGE_SUCCESS.getMessage(), this.getMyPageUserCase.getMyPageResponse(user.getSocialId())));
    }

    /**
     * 프로필 조회
     * [GET] api/mypage/profile
     * 작성자 : 김민수
     */
    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse<GetProfileResponse>> getProfile(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(SuccessResponse.create(GET_PROFILE_SUCCESS.getMessage(), this.profileUserCase.getProfile(user.getSocialId())));
    }

    /**
     * 프로필 수정
     * [PUT] api/mypage/profile
     * 작성자 : 김민수
     */
    @PutMapping("/profile")
    public ResponseEntity<SuccessResponse> updatePorfile(@AuthenticationPrincipal User user,
                                                         @RequestBody UpdateProfileRequest updateProfileRequest){
        this.profileUserCase.updateProfile(user.getSocialId(), updateProfileRequest);
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_PROFILE_SUCCESS.getMessage()));
    }

    /**
     * 알림정보 조회
     * [GET] api/mypage/alarm
     * 작성자 : 김민수
     */
    @GetMapping("/alarm")
    public ResponseEntity<SuccessResponse<GetAlarmResponse>> getAlarm(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(SuccessResponse.create(GET_ALARM_SUCCESS.getMessage(), this.alarmUserCase.getAlarm(user.getSocialId())));
    }

    /**
     * 알림정보 수정
     * [POST] api/mypage/alarm?type=all || isNewUploadPush || isRemindPush || isFirePush && status= on || off
     */
    @PutMapping("/alarm")
    public ResponseEntity<SuccessResponse> updateAlarm(@AuthenticationPrincipal User user,
                                                       @RequestParam(name = "type") String type,
                                                       @RequestParam(name = "status") String status) {
        this.alarmUserCase.updateAlarm(user.getSocialId(), type, status);
        return ResponseEntity.ok(SuccessResponse.create(UPDATE_PROFILE_SUCCESS.getMessage()));
    }

}
