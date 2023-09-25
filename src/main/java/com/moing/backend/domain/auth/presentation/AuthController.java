package com.moing.backend.domain.auth.presentation;

import com.moing.backend.domain.auth.application.dto.request.SignInRequest;
import com.moing.backend.domain.auth.application.dto.request.SignUpRequest;
import com.moing.backend.domain.auth.application.dto.request.TestRequest;
import com.moing.backend.domain.auth.application.dto.response.CheckNicknameResponse;
import com.moing.backend.domain.auth.application.dto.response.ReissueTokenResponse;
import com.moing.backend.domain.auth.application.dto.response.SignInResponse;
import com.moing.backend.domain.auth.application.service.CheckNicknameUserCase;
import com.moing.backend.domain.auth.application.service.ReissueTokenUserCase;
import com.moing.backend.domain.auth.application.service.SignInUserCase;
import com.moing.backend.domain.auth.application.service.SignUpUserCase;
import com.moing.backend.global.response.SuccessResponse;
import com.moing.backend.global.response.TokenInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import static com.moing.backend.domain.auth.presentation.constant.AuthResponseMessage.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final SignInUserCase authService;

    private final SignUpUserCase signUpService;

    private final ReissueTokenUserCase reissueTokenService;
    private final CheckNicknameUserCase checkNicknameService;

    /**
     * 소셜 로그인 (애플/ 카카오/구글)
     * [POST] api/auth/signIn/kakao||apple||google
     * 작성자 : 김민수
     */
    @PostMapping("/signIn/{provider}")
    public ResponseEntity<SuccessResponse<SignInResponse>> signIn(@PathVariable String provider,
                                                                  @Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(SuccessResponse.create(SIGN_IN_SUCCESS.getMessage(), this.authService.signIn(signInRequest.getToken(), provider)));
    }

    /**
     * 회원가입 (초기 로그인한 사용자 닉네임 입력)
     * [PUT] api/auth/signUp
     * 작성자 : 김민수
     */
    @PutMapping("/signUp")
    public ResponseEntity<SuccessResponse<SignInResponse>> signUp(@RequestHeader(value = "Authorization") String token,
                                                                  @Valid @RequestBody SignUpRequest signUpRequest) {
        token = (token != null && token.startsWith("Bearer ")) ? token.substring(7) : token;
        return ResponseEntity.ok(SuccessResponse.create(SIGN_UP_SUCCESS.getMessage(), this.signUpService.signUp(token, signUpRequest)));
    }
    /**
     * 토큰 재발급
     * [GET] api/auth/reissue
     * 작성자 : 김민수
     */
    @GetMapping("/reissue")
    public ResponseEntity<SuccessResponse<ReissueTokenResponse>> reissue(@RequestHeader(value = "RefreshToken") String token) {
        ReissueTokenResponse reissueToken = reissueTokenService.reissueToken(token);
        return ResponseEntity.ok(SuccessResponse.create(REISSUE_TOKEN_SUCCESS.getMessage(), reissueToken));
    }

    /**
     * 닉네임 중복검사
     * [GET] api/auth/nickname
     * 작성자 : 김민수
     */
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<SuccessResponse<CheckNicknameResponse>> checkNickname(@PathVariable String nickname){
        return ResponseEntity.ok(SuccessResponse.create(CHECK_NICKNAME_SUCCESS.getMessage(), checkNicknameService.checkNickname(nickname)));
    }

    /**
     * 테스트 계정 로그인 (토큰 만드는 컨트롤러)
     * [POST] api/auth/test
     * 작성자: 김민수
     */
    @PostMapping("/test/{provider}")
    public ResponseEntity<SuccessResponse<SignInResponse>> testLogin(@PathVariable String provider,
                                                                     @RequestBody TestRequest testRequest){
        return ResponseEntity.ok(SuccessResponse.create(SIGN_IN_SUCCESS.getMessage(), this.authService.testSignIn(testRequest.getSocialId(),provider)));
    }
}