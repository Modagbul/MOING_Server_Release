package com.moing.backend.domain.mission.representation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import com.moing.backend.domain.mission.application.dto.res.MissionReadRes;
import com.moing.backend.domain.mission.application.service.MissionCreateUseCase;
import com.moing.backend.domain.mission.application.service.MissionDeleteUseCase;
import com.moing.backend.domain.mission.application.service.MissionReadUseCase;
import com.moing.backend.domain.mission.application.service.MissionUpdateUseCase;
import com.moing.backend.domain.mission.domain.repository.MissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class MissionControllerTest extends CommonControllerTest {

    @MockBean
    private MissionCreateUseCase missionCreateUseCase;
    @MockBean
    private MissionUpdateUseCase missionUpdateUseCase;
    @MockBean
    private MissionReadUseCase missionReadUseCase;
    @MockBean
    private MissionDeleteUseCase missionDeleteUseCase;

    @Autowired
    private MissionRepository missionRepository;

    @Test
    public void 미션_생성() throws Exception {
        //given
        MissionReq input = MissionReq.builder()
                .title("title")
                .dueTo("dueTo")
                .rule("rule")
                .content("content")
                .number(1)
                .type("ONCE")
                .way("TEXT")
                .build();

        String body = objectMapper.writeValueAsString(input);

        MissionCreateRes output = MissionCreateRes.builder()
                .title("title")
                .dueTo("dueTo")
                .rule("rule")
                .content("content")
                .number(1L)
                .type("TEXT")
                .status("WAIT")
                .way("TEXT")
                .build();

        given(missionCreateUseCase.createMission(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                post("/mission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("token").description("카카오 액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.title").description("미션 제목"),
                                        fieldWithPath("data.dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("data.rule").description("미션 규칙"),
                                        fieldWithPath("data.content").description("미션 내용"),
                                        fieldWithPath("data.number").description("미션 반복 횟수"),
                                        fieldWithPath("data.type").description("미션 타입"),
                                        fieldWithPath("data.way").description("미션 진행 방법")
                                )
                        )
                );
    }

    @Test
    public void 미션_수정() throws Exception {
        //given
        MissionReq input = MissionReq.builder()
                .title("title")
                .dueTo("dueTo")
                .rule("rule")
                .content("content")
                .number(1)
                .type("ONCE")
                .way("TEXT")
                .build();

        String body = objectMapper.writeValueAsString(input);

        MissionCreateRes output = MissionCreateRes.builder()
                .title("title")
                .dueTo("dueTo")
                .rule("rule")
                .content("content")
                .number(1L)
                .type("TEXT")
                .status("WAIT")
                .way("TEXT")
                .build();

        given(missionCreateUseCase.createMission(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                put("/mission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("token").description("카카오 액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.title").description("미션 제목"),
                                        fieldWithPath("data.dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("data.rule").description("미션 규칙"),
                                        fieldWithPath("data.content").description("미션 내용"),
                                        fieldWithPath("data.number").description("미션 반복 횟수"),
                                        fieldWithPath("data.type").description("미션 타입"),
                                        fieldWithPath("data.way").description("미션 진행 방법")
                                )
                        )
                );
    }

    @Test
    public void 미션_조회() throws Exception {
        //given


        MissionReadRes output = MissionReadRes.builder()
                .title("title")
                .dueTo("dueTo")
                .rule("rule")
                .content("content")
                .type("TEXT")
                .way("TEXT")
                .build();

        given(missionReadUseCase.getMission(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                get("/mission/{missionId}")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                      parameterWithName("missionId").description("미션 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("token").description("카카오 액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("로그인을 했습니다"),
                                        fieldWithPath("data.title").description("미션 제목"),
                                        fieldWithPath("data.dueTo").description("미션 마감 날짜"),
                                        fieldWithPath("data.rule").description("미션 규칙"),
                                        fieldWithPath("data.content").description("미션 내용"),
                                        fieldWithPath("data.type").description("미션 타입"),
                                        fieldWithPath("data.way").description("미션 진행 방법")

                                )
                        )
                );
    }

    @Test
    public void 미션_삭제() throws Exception {
        //given

        MissionCreateRes output = MissionCreateRes.builder()
                .title("title")
                .dueTo("dueTo")
                .rule("rule")
                .content("content")
                .number(1L)
                .type("TEXT")
                .status("WAIT")
                .way("TEXT")
                .build();

        given(missionCreateUseCase.createMission(any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(
                delete("/mission/{missionId}")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("missionId").description("삭제할 미션 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("token").description("카카오 액세스 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("missionId").description("삭제된 미션 아이디")
                                )
                        )
                );
    }
}
