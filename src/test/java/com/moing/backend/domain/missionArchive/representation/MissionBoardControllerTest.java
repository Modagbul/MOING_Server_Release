package com.moing.backend.domain.missionArchive.representation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.RepeatMissionBoardRes;
import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
import com.moing.backend.domain.missionArchive.application.service.MissionArchiveBoardUseCase;
import com.moing.backend.domain.missionArchive.presentation.MissionBoardController;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@WebMvcTest(MissionBoardController.class)
public class MissionBoardControllerTest extends CommonControllerTest {


    @MockBean
    private MissionArchiveBoardUseCase missionArchiveBoardUseCase;

    @Test
    public void 단일_미션_인증_조회() throws Exception {
        //given

        List<SingleMissionBoardRes> output = Lists.newArrayList(SingleMissionBoardRes.builder()
                .missionId(1L)
                .dueTo("2023-09-03T21:32:33.888")
                .title("Mission title")
                .status("SKIP/COMPLETE")
                .missionType("ONCE/REPEAT")
                .build());

        given(missionArchiveBoardUseCase.getActiveSingleMissions(any(),any())).willReturn(output);

        Long teamId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}/missions/board/single",teamId)
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
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(ACTIVE_SINGLE_MISSION_SUCCESS.getMessage()),
                                        fieldWithPath("data[].missionId").description("미션 아이디"),
                                        fieldWithPath("data[].dueTo").description("미션 마감 시각"),
                                        fieldWithPath("data[].title").description("미션 제목"),
                                        fieldWithPath("data[].status").description("미션 인증 상태"),
                                        fieldWithPath("data[].missionType").description("미션 타입")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 반복_미션_인증_조회() throws Exception {
        //given

        List<RepeatMissionBoardRes> output = Lists.newArrayList(RepeatMissionBoardRes.builder()
                .missionId(1L)
                .title("Mission title")
                .dueTo("True/False")
                .done(1L)
                .number(3)
                .build());

        given(missionArchiveBoardUseCase.getActiveRepeatMissions(any(),any())).willReturn(output);

        Long teamId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}/missions/board/repeat",teamId)
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
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(ACTIVE_REPEAT_MISSION_SUCCESS.getMessage()),
                                        fieldWithPath("data[].missionId").description("미션 아이디"),
                                        fieldWithPath("data[].title").description("미션 제목"),
                                        fieldWithPath("data[].dueTo").description("내일 리셋 상태 리턴, 일요일이면 true[True/False]"),
                                        fieldWithPath("data[].number").description("전체 횟수"),
                                        fieldWithPath("data[].done").description("인증한 횟수")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 종료된_인증_조회() throws Exception {
        //given

        List<FinishMissionBoardRes> output = Lists.newArrayList(FinishMissionBoardRes.builder()
                .missionId(1L)
                .title("Mission title")
                .dueTo("2023-09-03T21:32:33.888")
                .status("SUCCESS/FAIL")
                .missionType("ONCE/REPEAT")
                .build());

        given(missionArchiveBoardUseCase.getFinishMissions(any(),any())).willReturn(output);

        Long teamId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}/missions/board/finish",teamId)
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
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(FINISH_ALL_MISSION_SUCCESS.getMessage()),
                                        fieldWithPath("data[].missionId").description("미션 아이디"),
                                        fieldWithPath("data[].title").description("미션 제목"),
                                        fieldWithPath("data[].dueTo").description("내일 리셋 상태 리턴, 일요일이면 true[True/False]"),
                                        fieldWithPath("data[].status").description("미션 종료된 상태 (성공/실패)"),
                                        fieldWithPath("data[].missionType").description("미션 타입 (한번/반복)")

                                )
                        )
                )
                .andReturn();

    }


}