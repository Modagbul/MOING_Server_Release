package com.moing.backend.domain.mission.representation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionConfirmRes;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import com.moing.backend.domain.mission.application.service.MissionDeleteUseCase;
import com.moing.backend.domain.mission.application.service.MissionReadUseCase;
import com.moing.backend.domain.mission.application.service.MissionUpdateUseCase;
import com.moing.backend.domain.mission.domain.service.MissionQueryService;
import com.moing.backend.domain.mission.presentation.MissionController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.CONFIRM_MISSION_SUCCESS;
import static com.moing.backend.domain.mission.presentation.constant.MissionResponseMessage.RECOMMEND_MISSION_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@WebMvcTest(MissionController.class)
public class MissionControllerTest extends CommonControllerTest {

    @MockBean
    private MissionCreateUseCase missionCreateUseCase;

    @MockBean
    private MissionUpdateUseCase missionUpdateUseCase;

    @MockBean
    private MissionReadUseCase missionReadUseCase;

    @MockBean
    private MissionDeleteUseCase missionDeleteUseCase;

    @MockBean
    private MissionQueryService missionQueryService;



    @Test
    public void 미션_생성() throws Exception {
        //given
        MissionReq input = MissionReq.builder()
                .title("title")
                .dueTo("2023-12-31 23:39:22.333")
                .rule("rule")
                .content("content")
                .number(1)
                .type("ONCE/REPEAT")
                .way("TEXT/PHOTO/LINK")
                .build();

        String body = objectMapper.writeValueAsString(input);

        MissionCreateRes output = MissionCreateRes.builder()
                .missionId(1L)
                .title("title")
                .dueTo("2023-12-31 23:39:22.333")
                .rule("rule")
                .content("content")
                .number(1)
                .type("ONCE/REPEAT")
                .status("END/ONGOING/SUCCESS/FAIL")
                .way("TEXT/PHOTO/LINK")
                .isLeader(Boolean.FALSE)
                .build();

        given(missionCreateUseCase.createMission(any(),any(),any())).willReturn(output);

        Long teamId = 2L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/team/{teamId}/missions",teamId)
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
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("title").description("미션 제목"),
                                        fieldWithPath("dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("rule").description("미션 규칙"),
                                        fieldWithPath("content").description("미션 내용"),
                                        fieldWithPath("number").description("미션 반복 횟수"),
                                        fieldWithPath("type").description("미션 유형(단일/반복)"),
                                        fieldWithPath("way").description("미션 진행 방법")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.missionId").description("미션 아이디"),
                                        fieldWithPath("data.title").description("미션 제목"),
                                        fieldWithPath("data.dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("data.rule").description("미션 규칙"),
                                        fieldWithPath("data.content").description("미션 내용"),
                                        fieldWithPath("data.number").description("미션 반복 횟수"),
                                        fieldWithPath("data.type").description("미션 유형(단일/반복)"),
                                        fieldWithPath("data.way").description("미션 진행 방법(사진/글/링크)"),
                                        fieldWithPath("data.status").description("미션 진행 상태"),
                                        fieldWithPath("data.isLeader").description("미션 생성자 여부")
                                )
                        )
                )
                       .andReturn();

    }

    @Test
    public void 미션_수정() throws Exception {
        //given
        MissionReq input = MissionReq.builder()
                .title("title")
                .dueTo("2023-12-31 23:39:22.333")
                .rule("rule")
                .content("content")
                .number(1)
                .type("ONCE")
                .way("TEXT")
                .build();

        String body = objectMapper.writeValueAsString(input);

        MissionCreateRes output = MissionCreateRes.builder()
                .missionId(1L)
                .title("title")
                .dueTo("2023-12-31 23:39:22.333")
                .rule("rule")
                .content("content")
                .number(1)
                .type("ONCE")
                .status("END")
                .way("TEXT")
                .isLeader(Boolean.FALSE)
                .build();

        given(missionUpdateUseCase.updateMission(any(),any(),any())).willReturn(output);
        Long teamId = 2L;
        Long missionId = 2L;

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                put("/api/team/{teamId}/missions/{missionId}",teamId,missionId)
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
                                        fieldWithPath("title").description("미션 제목"),
                                        fieldWithPath("dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("rule").description("미션 규칙"),
                                        fieldWithPath("content").description("미션 내용"),
                                        fieldWithPath("number").description("미션 반복 횟수"),
                                        fieldWithPath("type").description("미션 타입"),
                                        fieldWithPath("way").description("미션 진행 방법")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.missionId").description("미션 아이디"),
                                        fieldWithPath("data.title").description("미션 제목"),
                                        fieldWithPath("data.dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("data.rule").description("미션 규칙"),
                                        fieldWithPath("data.content").description("미션 내용"),
                                        fieldWithPath("data.number").description("미션 반복 횟수"),
                                        fieldWithPath("data.type").description("미션 유형(ONCE/REPEAT)"),
                                        fieldWithPath("data.way").description("미션 진행 방법(TEXT/PHOTO/LINK)"),
                                        fieldWithPath("data.status").description("미션 진행 상태(END/ONGOING/SUCCESS/FAIL)"),
                                        fieldWithPath("data.isLeader").description("미션 생성자 여부")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 미션_조회() throws Exception {
        //given


        MissionReadRes output = MissionReadRes.builder()
                .title("title")
                .dueTo("2023-12-31 23:39:22.333")
                .rule("rule")
                .content("content")
                .type("ONCE")
                .way("TEXT")
                .isLeader(Boolean.FALSE)
                .build();

        given(missionReadUseCase.getMission(any(),any())).willReturn(output);

        Long teamId = 2L;
        Long missionId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}/missions/{missionId}",teamId,missionId)
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
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.title").description("미션 제목"),
                                        fieldWithPath("data.dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("data.rule").description("미션 규칙"),
                                        fieldWithPath("data.content").description("미션 내용"),
                                        fieldWithPath("data.way").description("미션 진행 방법(TEXT/PHOTO/LINK)"),
                                        fieldWithPath("data.type").description("미션 유형(ONCE/REPEAT)"),
                                        fieldWithPath("data.isLeader").description("미션 생성자 여부")


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
                delete("/api/team/{teamId}/missions/{missionId}",teamId,missionId)
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

    @Test
    public void 미션_추천() throws Exception {
        //given


        String output = "SPORTS/HABIT/TEST/STUDY/READING/ETC" ;

        given(missionReadUseCase.getTeamCategory(any())).willReturn(output);

        Long teamId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}/missions/recommend",teamId)
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
                                        fieldWithPath("message").description(RECOMMEND_MISSION_SUCCESS),
                                        fieldWithPath("data").description("팀 카테고리 [SPORTS/HABIT/TEST/STUDY/READING/ETC] ")
                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 미션_종료() throws Exception {
        //given


        MissionReadRes output = MissionReadRes.builder()
                .title("title")
                .dueTo("2023-12-31 23:39:22.333")
                .rule("rule")
                .content("content")
                .type("ONCE")
                .way("TEXT")
                .isLeader(Boolean.FALSE)
                .build();

        given(missionUpdateUseCase.terminateMissionByUser(any(),any())).willReturn(output);

        Long teamId = 2L;
        Long missionId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                put("/api/team/{teamId}/missions/{missionId}/end",teamId,missionId)
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
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.title").description("미션 제목"),
                                        fieldWithPath("data.dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("data.rule").description("미션 규칙"),
                                        fieldWithPath("data.content").description("미션 내용"),
                                        fieldWithPath("data.way").description("미션 진행 방법(TEXT/PHOTO/LINK)"),
                                        fieldWithPath("data.type").description("미션 유형(ONCE/REPEAT)"),
                                        fieldWithPath("data.isLeader").description("미션 생성자 여부")

                                )
                        )
                )
                .andReturn();

    }

    @Test
    public void 미션_설명_확인() throws Exception {
        //given
        Long teamId = 2L;
        Long missionId = 1L;
        MissionConfirmRes output=new MissionConfirmRes(missionId);

        given(missionReadUseCase.confirmMission(any(),any(),any())).willReturn(output);
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/team/{teamId}/missions/{missionId}/confirm",teamId,missionId)
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
                                        parameterWithName("missionId").description("확인 미션 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(CONFIRM_MISSION_SUCCESS.getMessage()),
                                        fieldWithPath("data.missionId").description("확인한 미션의 아이디")
                                )
                        )
                )
                .andReturn();
    }

}
