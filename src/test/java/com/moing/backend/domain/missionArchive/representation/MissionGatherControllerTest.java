package com.moing.backend.domain.missionArchive.representation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.mission.application.dto.res.*;
import com.moing.backend.domain.mission.application.service.MissionGatherBoardUseCase;
import com.moing.backend.domain.missionArchive.presentation.MissionGatherController;
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

@WebMvcTest(MissionGatherController.class)
public class MissionGatherControllerTest extends CommonControllerTest {


    @MockBean
    private MissionGatherBoardUseCase missionGatherBoardUseCase;

    @Test
    public void 미션_모아보기_단일_미션() throws Exception {
        //given

        List<GatherSingleMissionRes> output = Lists.newArrayList(GatherSingleMissionRes.builder()
                .missionId(1L)
                .dueTo("2023-09-03T21:32:33.888")
                .teamName("team name")
                .missionTitle("mission title")
                .build());

        given(missionGatherBoardUseCase.getAllActiveSingleMissions(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/my-once")
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

                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(ACTIVE_SINGLE_MISSION_SUCCESS.getMessage()),
                                        fieldWithPath("data[].missionId").description("미션 아이디"),
                                        fieldWithPath("data[].dueTo").description("미션 마감 시각"),
                                        fieldWithPath("data[].teamName").description("팀 이름"),
                                        fieldWithPath("data[].missionTitle").description("미션 제목")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 미션_모아보기_반복_미션() throws Exception {
        //given

        List<GatherRepeatMissionRes> output = Lists.newArrayList(GatherRepeatMissionRes.builder()
                .missionId(1L)
                .teamName("team name")
                .missionTitle("mission title")
                .doneNum("0")
                .totalNum("0")
                .build());

        given(missionGatherBoardUseCase.getAllActiveRepeatMissions(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/my-repeat")
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

                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(ACTIVE_REPEAT_MISSION_SUCCESS.getMessage()),
                                        fieldWithPath("data[].missionId").description("미션 아이디"),
                                        fieldWithPath("data[].teamName").description("팀 이름"),
                                        fieldWithPath("data[].missionTitle").description("미션 제목"),
                                        fieldWithPath("data[].doneNum").description("완료한 횟수"),
                                        fieldWithPath("data[].totalNum").description("전체 횟수")


                                )
                        )
                )
                .andReturn();

    }



}