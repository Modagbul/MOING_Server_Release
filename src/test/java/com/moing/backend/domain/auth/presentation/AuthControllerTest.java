package com.moing.backend.domain.auth.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.auth.application.dto.request.SignInRequest;
import com.moing.backend.domain.auth.application.dto.request.SignUpRequest;
import com.moing.backend.domain.auth.application.dto.response.CheckNicknameResponse;
import com.moing.backend.domain.auth.application.dto.response.ReissueTokenResponse;
import com.moing.backend.domain.auth.application.dto.response.SignInResponse;
import com.moing.backend.domain.auth.application.service.CheckNicknameUseCase;
import com.moing.backend.domain.auth.application.service.ReissueTokenUseCase;
import com.moing.backend.domain.auth.application.service.SignInUseCase;
import com.moing.backend.domain.auth.application.service.SignUpUseCase;
import com.moing.backend.domain.member.domain.constant.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
class AuthControllerTest extends CommonControllerTest {

    @MockBean
    private SignInUseCase authService;

    @MockBean
    private SignUpUseCase signUpUseCase;

    @MockBean
    private ReissueTokenUseCase reissueTokenUseCase;

    @MockBean
    private CheckNicknameUseCase checkNicknameService;

    @Test
    public void Kakao_소셜_로그인_회원가입_전() throws Exception {
        //given
        SignInRequest input = SignInRequest.builder()
                .fcmToken("FCM_TOKEN")
                .socialToken("KAKAO_ACCESS_TOKEN")
                .build();

        String body = objectMapper.writeValueAsString(input);

        SignInResponse output = SignInResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .registrationStatus(false)
                .build();

        given(authService.signIn(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/auth/signIn/kakao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("fcmToken").description("FCM TOKEN"),
                                        fieldWithPath("socialToken").description("카카오 액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Access Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token"),
                                        fieldWithPath("data.registrationStatus").description("회원가입 여부 : false")
                                )
                        )
                );
    }

    @Test
    public void Kakao_소셜_로그인_회원가입_후() throws Exception {
        //given
        SignInRequest input = SignInRequest.builder()
                .fcmToken("FCM_TOKEN")
                .socialToken("KAKAO_ACCESS_TOKEN")
                .build();

        String body = objectMapper.writeValueAsString(input);

        SignInResponse output = SignInResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .registrationStatus(true)
                .build();

        given(authService.signIn(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/auth/signIn/kakao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("fcmToken").description("FCM TOKEN"),
                                        fieldWithPath("socialToken").description("카카오 액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Access Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token"),
                                        fieldWithPath("data.registrationStatus").description("회원가입 여부 :true")
                                )
                        )
                );
    }

    @Test
    public void Apple_소셜_로그인_회원가입_전() throws Exception {
        //given
        SignInRequest input = SignInRequest.builder()
                .fcmToken("FCM_TOKEN")
                .socialToken("APPLE_IDENTITY_TOKEN")
                .build();

        String body = objectMapper.writeValueAsString(input);

        SignInResponse output = SignInResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .registrationStatus(false)
                .build();

        given(authService.signIn(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/auth/signIn/apple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("fcmToken").description("FCM TOKEN"),
                                        fieldWithPath("socialToken").description("애플 아이디 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Access Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token"),
                                        fieldWithPath("data.registrationStatus").description("회원가입 여부 :false")
                                )
                        )
                );
    }

    @Test
    public void Apple_소셜_로그인_회원가입_후() throws Exception {
        //given
        SignInRequest input = SignInRequest.builder()
                .fcmToken("FCM_TOKEN")
                .socialToken("APPLE_IDENTITY_TOKEN")
                .build();

        String body = objectMapper.writeValueAsString(input);

        SignInResponse output = SignInResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .registrationStatus(true)
                .build();

        given(authService.signIn(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/auth/signIn/apple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("fcmToken").description("FCM TOKEN"),
                                        fieldWithPath("socialToken").description("애플 아이디 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Access Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token"),
                                        fieldWithPath("data.registrationStatus").description("회원가입 여부 :true")
                                )
                        )
                );
    }

    @Test
    public void GOOGLE_소셜_로그인_회원가입_전() throws Exception {
        //given
        SignInRequest input = SignInRequest.builder()
                .fcmToken("FCM_TOKEN")
                .socialToken("APPLE_IDENTITY_TOKEN")
                .build();

        String body = objectMapper.writeValueAsString(input);

        SignInResponse output = SignInResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .registrationStatus(false)
                .build();

        given(authService.signIn(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/auth/signIn/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("fcmToken").description("FCM TOKEN"),
                                        fieldWithPath("socialToken").description("애플 아이디 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Access Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token"),
                                        fieldWithPath("data.registrationStatus").description("회원가입 여부 :false")
                                )
                        )
                );
    }

    @Test
    public void GOOGLE_소셜_로그인_회원가입_후() throws Exception {
        //given
        SignInRequest input = SignInRequest.builder()
                .fcmToken("FCM_TOKEN")
                .socialToken("APPLE_IDENTITY_TOKEN")
                .build();

        String body = objectMapper.writeValueAsString(input);

        SignInResponse output = SignInResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .registrationStatus(true)
                .build();

        given(authService.signIn(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/api/auth/signIn/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("fcmToken").description("FCM TOKEN"),
                                        fieldWithPath("socialToken").description("애플 아이디 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Access Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token"),
                                        fieldWithPath("data.registrationStatus").description("회원가입 여부 :true")
                                )
                        )
                );
    }


    @Test
    public void sign_up() throws Exception {
        //given
        SignUpRequest input = SignUpRequest.builder()
                .nickName("NICKNAME")
                .gender(Gender.MAN)
                .birthDate("2000-03-28")
                .build();

        String body = objectMapper.writeValueAsString(input);

        SignInResponse output = SignInResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .registrationStatus(true)
                .build();


        given(signUpUseCase.signUp(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                put("/api/auth/signUp")
                        .header("Authorization", "Bearer ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );


        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("nickName").description("유저 닉네임"),
                                        fieldWithPath("gender").description("성별"),
                                        fieldWithPath("birthDate").description("태어난 날짜(YYYY-MM-DD)")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("회원 가입을 했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Access Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token"),
                                        fieldWithPath("data.registrationStatus").description("회원가입 여부 : true")
                                )
                        )
                );
    }
    @Test
    public void reissue_token() throws Exception {
        //given
        ReissueTokenResponse output = ReissueTokenResponse.builder()
                .accessToken("SERVER_ACCESS_TOKEN")
                .refreshToken("SERVER_REFRESH_TOKEN")
                .build();

        given(reissueTokenUseCase.reissueToken(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                get("/api/auth/reissue")
                        .header("RefreshToken", "REFRESH_TOKEN")
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("RefreshToken").description("토큰 재발급용 RefreshToken")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("성공 여부 : true"),
                                        fieldWithPath("message").description("토큰을 재발급했습니다"),
                                        fieldWithPath("data.accessToken").description("서버 접근용 Token"),
                                        fieldWithPath("data.refreshToken").description("서버 접근용 Refresh Token")
                                )
                        )
                );
    }

    @Test
    public void check_nickname_중복O() throws Exception {
        //given
        CheckNicknameResponse output = new CheckNicknameResponse(true);
        given(checkNicknameService.checkNickname(any())).willReturn(output);


        // when
        ResultActions actions = mockMvc.perform(
                get("/api/auth/checkNickname?nickname=minsu")
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("nickname").description("중복검사할 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").description("성공 여부 : true"),
                                fieldWithPath("message").description("닉네임 중복검사를 했습니다"),
                                fieldWithPath("data.isDuplicated").description("닉네임 중복 여부 : ture") // 이 부분은 CheckNicknameResponse의 구조에 따라 변경될 수 있습니다.
                        )
                ));
    }

    @Test
    public void check_nickname_중복X() throws Exception {
        //given
        CheckNicknameResponse output = new CheckNicknameResponse(false);
        given(checkNicknameService.checkNickname(any())).willReturn(output);


        // when
        ResultActions actions = mockMvc.perform(
                get("/api/auth/checkNickname?nickname=minsu")
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestParameters(
                                parameterWithName("nickname").description("중복검사할 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("isSuccess").description("성공 여부 : true"),
                                fieldWithPath("message").description("닉네임 중복검사를 했습니다"),
                                fieldWithPath("data.isDuplicated").description("닉네임 중복 여부 : false") // 이 부분은 CheckNicknameResponse의 구조에 따라 변경될 수 있습니다.
                        )
                ));
    }

}