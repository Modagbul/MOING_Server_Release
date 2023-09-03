package com.moing.backend.domain.mypage.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.mypage.application.dto.request.UpdateProfileRequest;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.application.dto.response.GetProfileResponse;
import com.moing.backend.domain.mypage.application.service.AlarmUserCase;
import com.moing.backend.domain.mypage.application.service.ProfileUserCase;
import com.moing.backend.domain.mypage.application.service.SignOutUserCase;
import com.moing.backend.domain.mypage.application.service.WithdrawUserCase;
import com.moing.backend.domain.team.application.dto.response.CreateTeamResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyPageController.class)
public class MypageControllerTest extends CommonControllerTest {
    @MockBean
    private SignOutUserCase signOutService;

    @MockBean
    private WithdrawUserCase withdrawService;

    @MockBean
    private ProfileUserCase profileUserCase;

    @MockBean
    private AlarmUserCase alarmUserCase;

    @Test
    public void sign_out() throws Exception {
        //when
        ResultActions actions = mockMvc.perform(
                post("/api/mypage/signOut")
                        .header("Authorization", "Bearer ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
        );


        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그아웃을 했습니다")
                                )
                        )
                );
    }

    @Test
    public void withdraw() throws Exception {
        //given
        WithdrawRequest input = WithdrawRequest.builder()
                .reason("REASON_TO_LEAVE")
                .build();

        String body = objectMapper.writeValueAsString(input);


        //when
        ResultActions actions = mockMvc.perform(
                delete("/api/mypage/withdrawal")
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
                                        fieldWithPath("reason").description("회원탈퇴하는 이유")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("회원탈퇴를 했습니다")
                                )
                        )
                );
    }

    @Test
    public void get_profile() throws Exception {
        //given
        GetProfileResponse output = GetProfileResponse.builder()
                .profileImage("PROFILE_IMAGE_URL")
                .introduction("INTRODUCTION")
                .nickName("NICKNAME")
                .build();

        given(profileUserCase.getProfile(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                get("/api/mypage/profile")
                        .header("Authorization", "Bearer ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
        );


        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("프로필을 조회했습니다"),
                                        fieldWithPath("data.profileImage").description("프로필 이미지 URL"),
                                        fieldWithPath("data.nickName").description("닉네임"),
                                        fieldWithPath("data.introduction").description("한줄 소개")
                                )
                        )
                );
    }

    @Test
    public void update_profile() throws Exception {
        //given
        UpdateProfileRequest input = UpdateProfileRequest.builder()
                .profileImage("PROFILE_IMAGE_URL")
                .introduction("INTRODUCTION")
                .nickName("NICKNAME")
                .build();

        String body = objectMapper.writeValueAsString(input);

        //when
        ResultActions actions = mockMvc.perform(
                put("/api/mypage/profile")
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
                                        fieldWithPath("profileImage").description("프로필 이미지 URL"),
                                        fieldWithPath("nickName").description("닉네임"),
                                        fieldWithPath("introduction").description("한줄 소개")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("프로필을 수정했습니다.")
                                )
                        )
                );
    }
}
