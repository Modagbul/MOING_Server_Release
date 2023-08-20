package com.moing.backend.domain.auth.presentation;

import com.moing.backend.domain.auth.application.dto.request.SignInRequest;
import com.moing.backend.domain.auth.application.dto.request.SignUpRequest;
import com.moing.backend.domain.auth.application.dto.response.ReissueTokenResponse;
import com.moing.backend.domain.auth.application.dto.response.SignInResponse;
import com.moing.backend.domain.auth.application.service.ReissueTokenUserCase;
import com.moing.backend.domain.auth.application.service.SignInUserCase;
import com.moing.backend.domain.auth.application.service.SignUpUserCase;
import com.moing.backend.global.response.SuccessResponse;
import com.moing.backend.global.response.TokenInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.moing.backend.domain.auth.presentation.constant.AuthResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final SignInUserCase authService;

    private final SignUpUserCase signUpService;

    private final ReissueTokenUserCase reissueTokenService;

    /**
     * 소셜 로그인 (애플/ 카카오)
     * [POST] /auth/signIn/kakao||apple
     * 작성자 : 김민수
     */
    @PostMapping("/signIn/{provider}")
    public ResponseEntity<SuccessResponse<SignInResponse>> signIn(@PathVariable String provider,
                                                                  @Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(SuccessResponse.create(SIGN_IN_SUCCESS.getMessage(), this.authService.signIn(signInRequest.getToken(), provider)));
    }

    /**
     * 회원가입 (초기 로그인한 사용자 닉네임 입력)
     * [PUT] /auth/signUp
     * 작성자 : 김민수
     */
    @PutMapping("/signUp")
    public ResponseEntity<SuccessResponse<SignInResponse>> signUp(HttpServletRequest request, @Valid @RequestBody SignUpRequest signUpRequest) {
        String token = request.getHeader("Authorization");
        return ResponseEntity.ok(SuccessResponse.create(SIGN_UP_SUCCESS.getMessage(), this.signUpService.signUp(token, signUpRequest)));
    }


    /**
     * 토큰 재발급
     * [GET] /auth/reissue
     * 작성자 : 김민수
     */
    @GetMapping("/reissue")
    public ResponseEntity<SuccessResponse<ReissueTokenResponse>> reissue(HttpServletRequest request) {

        // 헤더로부터 RefreshToken 추출.
        String token = request.getHeader("RefreshToken");
        // 토큰 재발행
        ReissueTokenResponse reissueToken = reissueTokenService.reissueToken(token);

        return ResponseEntity.ok(SuccessResponse.create(REISSUE_TOKEN_SUCCESS.getMessage(), reissueToken));
    }

    //TODO 닉네임 중복검사
}