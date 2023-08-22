package com.moing.backend.domain.mypage.presentation;

import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.application.service.SignOutUserCase;
import com.moing.backend.domain.mypage.application.service.WithdrawUserCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.moing.backend.domain.mypage.presentation.constant.MypageResponseMessage.SIGN_OUT_SUCCESS;
import static com.moing.backend.domain.mypage.presentation.constant.MypageResponseMessage.WITHDRAWAL_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

    private final SignOutUserCase signOutService;
    private final WithdrawUserCase withdrawService;

    //TODO 마이페이지 조회, 수정, 알림설정 수정

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
}
