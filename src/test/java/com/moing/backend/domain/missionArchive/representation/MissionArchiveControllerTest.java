package com.moing.backend.domain.missionArchive.representation;

import com.google.common.collect.ImmutableList;
import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import com.moing.backend.domain.mission.application.service.MissionDeleteUseCase;
import com.moing.backend.domain.mission.application.service.MissionReadUseCase;
import com.moing.backend.domain.mission.application.service.MissionUpdateUseCase;
import com.moing.backend.domain.mission.presentation.MissionController;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchive;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveCreateUseCase;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveHeartUseCase;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveUpdateUseCase;
import com.moing.backend.domain.missionArchive.application.service.SingleMissionArchiveReadUseCase;
import com.moing.backend.domain.missionArchive.presentation.MissionArchiveController;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@WebMvcTest(MissionArchiveController.class)
public class MissionArchiveControllerTest extends CommonControllerTest {

    @MockBean
    private MissionArchiveCreateUseCase missionArchiveCreateUseCase;
    @MockBean
    private MissionArchiveUpdateUseCase missionArchiveUpdateUseCase;
    @MockBean
    private SingleMissionArchiveReadUseCase singleMissionArchiveReadUseCase;
    @MockBean
    private MissionArchiveHeartUseCase missionArchiveHeartUseCase;



    @Test
    public void 미션_인증하기() throws Exception {
        //given
        MissionArchiveReq input = MissionArchiveReq.builder()
                .status("COMPLETE")
                .archive("https://iwheifhoaiwj")
                .build();

        String body = objectMapper.writeValueAsString(input);

        MissionArchiveRes output = MissionArchiveRes.builder()
                .archiveId(1L)
                .archive("https://iwheifhoaiwj")
                .createdDate("2023-09-03T21:32:33.888")
                .hearts(3)
                .build();

        given(missionArchiveCreateUseCase.createArchive(any(),any(),any())).willReturn(output);

        Long teamId = 2L;
        Long missionId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/{teamId}/missions/{missionId}/archive",teamId,missionId)
                        .header("Authorization", "Bearer ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("missionId").description("미션 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("status").description("미션 인증 상태 [COMPLETE/SKIP]"),
                                        fieldWithPath("archive").description("미션 인증물 [s3URL/text/링크] ")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("미션 인증을 완료 했습니다."),
                                        fieldWithPath("data.archiveId").description("미션 인증 아이디"),
                                        fieldWithPath("data.createdDate").description("미션 제출 시각"),
                                        fieldWithPath("data.hearts").description("미션 인증 좋아요 수")
//                                        fieldWithPath("data.heartsStatus").description("미션 인증 좋아요 상태"),
                                )
                        )
                )
                       .andReturn();

    }

    @Test
    public void 미션_수정() throws Exception {
        //given
        MissionArchiveReq input = MissionArchiveReq.builder()
                .status("COMPLETE")
                .archive("https://iwheifhoaiwj")
                .build();

        String body = objectMapper.writeValueAsString(input);

        MissionArchiveRes output = MissionArchiveRes.builder()
                .archiveId(1L)
                .archive("https://iwheifhoaiwj")
                .createdDate("2023-09-03T21:32:33.888")
                .hearts(3)
                .build();

        given(missionArchiveCreateUseCase.createArchive(any(),any(),any())).willReturn(output);

        Long teamId = 2L;
        Long missionId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                put("/api/{teamId}/missions/{missionId}/archive",teamId,missionId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("missionId").description("미션 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("status").description("미션 인증 상태 [COMPLETE/SKIP]"),
                                        fieldWithPath("archive").description("미션 인증물 [s3URL/text/링크] ")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("미션 인증을 완료 했습니다."),
                                        fieldWithPath("data.archiveId").description("미션 인증 아이디"),
                                        fieldWithPath("data.createdDate").description("미션 제출 시각"),
                                        fieldWithPath("data.hearts").description("미션 인증 좋아요 수")
//                                        fieldWithPath("data.heartsStatus").description("미션 인증 좋아요 상태"),
                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 나의_미션_인증_조회() throws Exception {
        //given


        MissionArchiveRes output = MissionArchiveRes.builder()
                .archiveId(1L)
                .archive("https://iwheifhoaiwj")
                .createdDate("2023-09-03T21:32:33.888")
                .hearts(3)
                .build();

        given(singleMissionArchiveReadUseCase.getMyArchive(any(),any())).willReturn(output);

        Long teamId = 2L;
        Long missionId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/{teamId}/missions/{missionId}/archive",teamId,missionId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)

        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("missionId").description("미션 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("나의 미션 인증 현황 조회를 완료 했습니다."),
                                        fieldWithPath("data.archiveId").description("미션 인증 아이디"),
                                        fieldWithPath("data.createdDate").description("미션 제출 시각"),
                                        fieldWithPath("data.hearts").description("미션 인증 좋아요 수")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 모임원_미션_인증_조회() throws Exception {
        //given

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


        List<PersonalArchive> output =  Lists.newArrayList(PersonalArchive.builder()
                .archiveId(1L)
                .nickname("nickname")
                .profileImg("https://wehofijaowi")
                .archive("hihihihi")
                .createdDate("2023-09-03T21:32:33.888")
                .hearts(3)
                .build());

        given(singleMissionArchiveReadUseCase.getPersonalArchive(any(),any())).willReturn(output);

        Long teamId = 1L;
        Long missionId = 5L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/{teamId}/missions/{missionId}/archive/others",teamId,missionId)
                .header("Authorization", "Bearer ACCESS_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)

        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("missionId").description("미션 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("팀원 미션 인증 현황 조회를 완료 했습니다."),
                                        fieldWithPath("data.archiveId").description("미션 인증 아이디"),
                                        fieldWithPath("data.nickname").description("미션 인증자 닉네임 "),
                                        fieldWithPath("data.profileImg").description("미션 인증자 프로필 이미지 "),
                                        fieldWithPath("data.archive").description("미션 인증물 [s3URL/text/링크] "),
                                        fieldWithPath("data.createdDate").description("미션 인증 날짜 "),
                                        fieldWithPath("data.hearts").description("미션 인증 좋아요 수 ")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 미션_삭제() throws Exception {
        //given


        Long teamId = 2L;
        Long missionId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/{teamId}/missions/{missionId}",teamId,missionId)
                        .header("Authorization", "Bearer ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("접근 토큰")
                                ),
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디"),
                                        parameterWithName("missionId").description("삭제할 미션 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("미션을 삭제 했습니다"),
                                        fieldWithPath("data").description("삭제된 미션 아이디")
                                )
                        )
                )
                .andReturn();
    }
}
