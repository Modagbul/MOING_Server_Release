package com.moing.backend.domain.fire.representation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.fire.application.dto.res.FireReceiveRes;
import com.moing.backend.domain.fire.application.dto.res.FireThrowRes;
import com.moing.backend.domain.fire.application.service.FireThrowUseCase;
import com.moing.backend.domain.fire.presentation.FireController;
import com.moing.backend.domain.mission.application.dto.req.MissionReq;
import com.moing.backend.domain.mission.application.dto.res.MissionCreateRes;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.moing.backend.domain.fire.presentation.constant.FireResponseMessage.GET_RECEIVERS_SUCCESS;
import static com.moing.backend.domain.fire.presentation.constant.FireResponseMessage.THROW_FIRE_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@WebMvcTest(FireController.class)
public class FireControllerTest extends CommonControllerTest {

    @MockBean
    private FireThrowUseCase fireThrowUseCase;


    @Test
    public void 불_던질_사람_조회() throws Exception {
        //given

        List<FireReceiveRes> output = Lists.newArrayList(FireReceiveRes.builder()
                .receiveMemberId(1L)
                .nickname("receiver 닉네임")
                .fireStatus("True/False")
                .build());

        given(fireThrowUseCase.getFireReceiveList(any(),any(),any())).willReturn(output);

        Long teamId = 1L;
        Long missionId = 1L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}/missions/{missionId}/fire",teamId,missionId)
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
                                        fieldWithPath("message").description(GET_RECEIVERS_SUCCESS.getMessage()),
                                        fieldWithPath("data[].receiveMemberId").description("미션 아이디"),
                                        fieldWithPath("data[].nickname").description("불 받을 사람 "),
                                        fieldWithPath("data[].fireStatus").description("불 던질 수 있는 상태 리턴, 1시간 내 불 던진 내역에 따라 true[True/False]")

                                )
                        )
                )
                .andReturn();

    }


    @Test
    public void 불_던지기() throws Exception {
        //given

        FireThrowRes output = FireThrowRes.builder()
                .receiveMemberId(1L)
                .build();

        given(fireThrowUseCase.createFireThrow(any(),any())).willReturn(output);

        Long teamId = 2L;
        Long missionId = 2L;
        Long receiveMemberId = 2L;
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/team/{teamId}/missions/{missionId}/fire/{receiveMemberId}",teamId,missionId,receiveMemberId)
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
                                        parameterWithName("missionId").description("미션 아이디"),
                                        parameterWithName("receiveMemberId").description("불 받을 사람 아이디")
                                ),

                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description(THROW_FIRE_SUCCESS.getMessage()),
                                        fieldWithPath("data.receiveMemberId").description("미션 아이디")
                                )
                        )
                )
                .andReturn();

    }


}
