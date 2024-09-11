package com.moing.backend.domain.mypage.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.mypage.application.dto.request.UpdateProfileRequest;
import com.moing.backend.domain.mypage.application.dto.request.WithdrawRequest;
import com.moing.backend.domain.mypage.application.dto.response.GetAlarmResponse;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageResponse;
import com.moing.backend.domain.mypage.application.dto.response.GetMyPageTeamBlock;
import com.moing.backend.domain.mypage.application.dto.response.GetProfileResponse;
import com.moing.backend.domain.mypage.application.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyPageController.class)
public class MypageControllerTest extends CommonControllerTest {
    @MockBean
    private SignOutUseCase signOutService;

    @MockBean
    private WithdrawUseCase withdrawService;

    @MockBean
    private ProfileUseCase profileUseCase;

    @MockBean
    private AlarmUseCase alarmUseCase;

    @MockBean
    private GetMyPageUseCase getMyPageUseCase;

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
                .socialToken("SOCIAL_TOKEN")
                .build();

        String body = objectMapper.writeValueAsString(input);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/mypage/withdrawal/{provider}","google,kakao,apple")
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
                                pathParameters(
                                        parameterWithName("provider").description("google, kakao, apple")
                                ),
                                requestFields(
                                        fieldWithPath("socialToken").description("google:accessToken/ kakao:accessToken/ apple:authorization code"),
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
    public void get_mypage() throws Exception{
        //given
        List<String> categoryList=new ArrayList<>();
        categoryList.add("SPORTS");

        List<GetMyPageTeamBlock> getMyPageTeamBlocks=new ArrayList<>();
        GetMyPageTeamBlock blocks= GetMyPageTeamBlock.builder()
                .teamId(1L)
                .teamName("소모임이름")
                .category("SPORTS")
                .profileImgUrl("프로필 이미지 url")
                .build();
        getMyPageTeamBlocks.add(blocks);

        GetMyPageResponse output = GetMyPageResponse.builder()
                .profileImage("PROFILE_IMAGE_URL")
                .introduction("INTRODUCTION")
                .nickName("NICKNAME")
                .categories(categoryList)
                .getMyPageTeamBlocks(getMyPageTeamBlocks)
                .build();

        given(getMyPageUseCase.getMyPageResponse(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                get("/api/mypage")
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
                                        fieldWithPath("message").description("마이페이지를 조회했습니다"),
                                        fieldWithPath("data.profileImage").description("프로필 이미지 URL"),
                                        fieldWithPath("data.nickName").description("닉네임"),
                                        fieldWithPath("data.introduction").description("한줄 소개"),
                                        fieldWithPath("data.categories[]").description("내 열정의 불 해시태그"),
                                        fieldWithPath("data.getMyPageTeamBlocks[].teamId").description("소모임 아이디"),
                                        fieldWithPath("data.getMyPageTeamBlocks[].teamName").description("소모임 이름"),
                                        fieldWithPath("data.getMyPageTeamBlocks[].category").description("소모임 카테고리"),
                                        fieldWithPath("data.getMyPageTeamBlocks[].profileImgUrl").description("소모임 프로필 이미지 URL")
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

        given(profileUseCase.getProfile(any())).willReturn(output);

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


    @Test
    public void get_alarm() throws Exception {
        //given
        GetAlarmResponse output = GetAlarmResponse.builder()
                .isNewUploadPush(true)
                .isFirePush(true)
                .isRemindPush(true)
                .isCommentPush(true)
                .build();

        given(alarmUseCase.getAlarm(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                get("/api/mypage/alarm")
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
                                        fieldWithPath("message").description("알람 정보를 조회했습니다"),
                                        fieldWithPath("data.newUploadPush").description("신규 공지 알림"),
                                        fieldWithPath("data.remindPush").description("미션 리마인드 알림"),
                                        fieldWithPath("data.firePush").description("불 던지기 알림"),
                                        fieldWithPath("data.commentPush").description("댓글 알림")
                                )
                        )
                );
    }

    @Test
    public void update_alarm() throws Exception {
        //given
        GetAlarmResponse output = GetAlarmResponse.builder()
                .isNewUploadPush(true)
                .isFirePush(true)
                .isRemindPush(true)
                .isCommentPush(true)
                .build();

        given(alarmUseCase.updateAlarm(any(),any(),any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                put("/api/mypage/alarm")
                        .header("Authorization", "Bearer ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("type", "all") // 파라미터 추가
                        .param("status", "on") // 파라미터 추가
        );


        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                requestParameters( // 요청 파라미터 문서화
                                        parameterWithName("type").description("all || isNewUploadPush || isRemindPush || isFirePush"),
                                        parameterWithName("status").description("on || off")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("알람 정보를 수정했습니다"),
                                        fieldWithPath("data.newUploadPush").description("신규 공지 알림"),
                                        fieldWithPath("data.remindPush").description("미션 리마인드 알림"),
                                        fieldWithPath("data.firePush").description("불 던지기 알림"),
                                        fieldWithPath("data.commentPush").description("댓글 알림")

                                )
                        )
                );
    }
}
