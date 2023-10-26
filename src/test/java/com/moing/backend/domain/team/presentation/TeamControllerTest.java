package com.moing.backend.domain.team.presentation;

import com.moing.backend.config.CommonControllerTest;
import com.moing.backend.domain.team.application.dto.request.CreateTeamRequest;
import com.moing.backend.domain.team.application.dto.request.UpdateTeamRequest;
import com.moing.backend.domain.team.application.dto.response.*;
import com.moing.backend.domain.team.application.service.*;
import com.moing.backend.domain.team.domain.constant.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamController.class)
public class TeamControllerTest extends CommonControllerTest {
    @MockBean
    private CreateTeamUserCase createTeamService;
    @MockBean
    private GetTeamUserCase getTeamUserCase;
    @MockBean
    private DisbandTeamUserCase disbandTeamUserCase;
    @MockBean
    private WithdrawTeamUserCase withdrawTeamUserCase;
    @MockBean
    private SignInTeamUserCase signInTeamUserCase;
    @MockBean
    private UpdateTeamUserCase updateTeamUserCase;

    @Test
    public void create_team() throws Exception {

        //given
        CreateTeamRequest input = CreateTeamRequest.builder()
                .category("ETC")
                .name("소모임 이름")
                .introduction("소모임 소개글")
                .profileImgUrl("소모임 대표 사진 URL")
                .promise("소모임 각오")
                .build();

        String body = objectMapper.writeValueAsString(input);

        CreateTeamResponse output = CreateTeamResponse.builder()
                .teamId(1L)
                .build();

        given(createTeamService.createTeam(any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(
                post("/api/team")
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
                                        fieldWithPath("category").description("카테고리"),
                                        fieldWithPath("name").description("소모임 이름"),
                                        fieldWithPath("introduction").description("소모임 소개글"),
                                        fieldWithPath("profileImgUrl").description("소모임 대표 사진"),
                                        fieldWithPath("promise").description("소모임 각오")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("소모임을 생성하였습니다"),
                                        fieldWithPath("data.teamId").description("생성한 teamId")
                                )
                        )
                );
    }
    @Test
    public void get_team() throws Exception {
        //given
        List<TeamBlock> teamBlocks=new ArrayList<>();

        TeamBlock teamBlock1=TeamBlock.builder()
                .teamId(1L)
                .duration(5L)
                .levelOfFire(3)
                .teamName("소모임 예시1")
                .numOfMember(10)
                .category("ETC")
                .startDate("2023.09.05")
                .deletionTime(LocalDateTime.now().withNano(0))
                .build();

        TeamBlock teamBlock2=TeamBlock.builder()
                .teamId(2L)
                .duration(10L)
                .levelOfFire(3)
                .teamName("소모임 예시2")
                .numOfMember(8)
                .category("SPORTS")
                .startDate("2023.09.01")
                .deletionTime(LocalDateTime.now().withNano(0))
                .build();

        teamBlocks.add(teamBlock1);
        teamBlocks.add(teamBlock2);

        GetTeamResponse output = GetTeamResponse.builder()
                .numOfTeam(1)
                .memberNickName("유저 닉네임")
                .teamBlocks(teamBlocks)
                .build();

        given(getTeamUserCase.getTeam(any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(
                get("/api/team")
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
                                        fieldWithPath("message").description("홈 화면에서 내 소모임을 모두 조회했습니다."),
                                        fieldWithPath("data.numOfTeam").description("소모임 개수(최대 3개)"),
                                        fieldWithPath("data.memberNickName").description("유저 닉네임"),
                                        fieldWithPath("data.teamBlocks[0].teamId").description("소모임 아이디"),
                                        fieldWithPath("data.teamBlocks[0].duration").description("소모임과 함께한 시간"),
                                        fieldWithPath("data.teamBlocks[0].levelOfFire").description("불꽃 레벨"),
                                        fieldWithPath("data.teamBlocks[0].teamName").description("소모임 이름"),
                                        fieldWithPath("data.teamBlocks[0].numOfMember").description("소모임원 명 수"),
                                        fieldWithPath("data.teamBlocks[0].category").description("소모임 카테고리"),
                                        fieldWithPath("data.teamBlocks[0].startDate").description("소모임 시작일"),
                                        fieldWithPath("data.teamBlocks[0].deletionTime").description("소모임 삭제 시간 (삭제 안했으면 null)")
                                )

                        )
                );
    }

    @Test
    public void get_team_detail() throws Exception {
        //given
        Long teamId = 1L;
        List<TeamMemberInfo> teamMemberInfoList = new ArrayList<>();
        TeamMemberInfo teamMemberInfo = TeamMemberInfo.builder()
                .memberId(1L)
                .nickName("소모임원 닉네임")
                .profileImage("소모임원 프로필 사진")
                .introduction("소모임원 소개")
                .isLeader(true)
                .build();
        teamMemberInfoList.add(teamMemberInfo);

        TeamInfo teamInfo = TeamInfo
                .builder()
                .isDeleted(true)
                .deletionTime(LocalDateTime.now())
                .teamName("소모임 이름")
                .numOfMember(1)
                .category(Category.ETC)
                .introduction("소모임 소개글")
                .teamMemberInfoList(teamMemberInfoList).build();

        GetTeamDetailResponse output = GetTeamDetailResponse
                .builder()
                .boardNum(1)
                .teamInfo(teamInfo)
                .build();


        given(getTeamUserCase.getTeamDetailResponse(any(),any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/board/{teamId}", teamId)
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
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("목표보드를 조회했습니다."),
                                        fieldWithPath("data.boardNum").description("안 읽은 공지/게시글"),
                                        fieldWithPath("data.teamInfo.teamName").description("소모임 이름"),
                                        fieldWithPath("data.teamInfo.numOfMember").description("모임원 명 수"),
                                        fieldWithPath("data.teamInfo.category").description("카테고리"),
                                        fieldWithPath("data.teamInfo.introduction").description("모임 소개"),
                                        fieldWithPath("data.teamInfo.isDeleted").description("삭제여부"),
                                        fieldWithPath("data.teamInfo.deletionTime").description("삭제 시간 (삭제 안했으면 null)"),
                                        fieldWithPath("data.teamInfo.teamMemberInfoList[0].memberId").description("유저 아이디"),
                                        fieldWithPath("data.teamInfo.teamMemberInfoList[0].nickName").description("유저 닉네임"),
                                        fieldWithPath("data.teamInfo.teamMemberInfoList[0].profileImage").description("유저 프로필 이미지"),
                                        fieldWithPath("data.teamInfo.teamMemberInfoList[0].introduction").description("유저 소개"),
                                        fieldWithPath("data.teamInfo.teamMemberInfoList[0].isLeader").description("유저 소모임장 여부")
                                )

                        )
                );
    }

    @Test
    public void disband_team() throws Exception {
        //given
        Long teamId = 1L; // 예시 ID
        DeleteTeamResponse output = DeleteTeamResponse.builder()
                .teamId(teamId)
                .teamName("팀 이름")
                .numOfMember(9)
                .duration(30L)
                .numOfMission(90L)
                .levelOfFire(3)
                .build();

        given(disbandTeamUserCase.disbandTeam(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/team/{teamId}/disband", teamId)
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
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("[소모임장 권한] 소모임을 강제 종료했습니다."),
                                        fieldWithPath("data.teamId").description("강제종료한 소모임 id"),
                                        fieldWithPath("data.teamName").description("소모임 이름"),
                                        fieldWithPath("data.numOfMember").description("모임원 명 수"),
                                        fieldWithPath("data.duration").description("소모임과 함께한 시간"),
                                        fieldWithPath("data.levelOfFire").description("소모임 불꽃 레벨"),
                                        fieldWithPath("data.numOfMission").description("미션 총 개수")
                                )
                        )
                );
    }
    @Test
    public void withdraw_team() throws Exception {
        //given
        Long teamId = 1L; // 예시 ID
        DeleteTeamResponse output = DeleteTeamResponse.builder()
                .teamId(teamId)
                .teamName("팀 이름")
                .numOfMember(9)
                .duration(30L)
                .numOfMission(90L)
                .levelOfFire(3)
                .build();

        given(withdrawTeamUserCase.withdrawTeam(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete("/api/team/{teamId}/withdraw", teamId)
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
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("[소모임원 권한] 소모임을 탈퇴하였습니다"),
                                        fieldWithPath("data.teamId").description("탈퇴한 소모임 id"),
                                        fieldWithPath("data.teamName").description("소모임 이름"),
                                        fieldWithPath("data.numOfMember").description("모임원 명 수"),
                                        fieldWithPath("data.duration").description("소모임과 함께한 시간"),
                                        fieldWithPath("data.levelOfFire").description("소모임 불꽃 레벨"),
                                        fieldWithPath("data.numOfMission").description("미션 총 개수")
                                )
                        )
                );
    }

    @Test
    public void signIn_team() throws Exception {
        //given
        Long teamId = 1L; // 예시 ID
        CreateTeamResponse output = CreateTeamResponse.builder()
                .teamId(teamId)
                .build();

        given(signInTeamUserCase.signInTeam(any(), any())).willReturn(output);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post("/api/team/{teamId}", teamId)
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
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("소모임에 가입하였습니다."),
                                        fieldWithPath("data.teamId").description("가입한 소모임 id")
                                )
                        )
                );
    }



    @Test
    public void update_team() throws Exception {

        // given
        Long teamId = 1L;
        UpdateTeamRequest input = UpdateTeamRequest.builder()
                .name("수정 후 팀 이름")
                .introduction("수정 후 소개")
                .profileImgUrl("수정 후 프로필 이미지")
                .build();


        String body = objectMapper.writeValueAsString(input);

        UpdateTeamResponse output = UpdateTeamResponse.builder()
                .teamId(1L)
                .build();

        given(updateTeamUserCase.updateTeam(any(), any(), any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                put("/api/team/{teamId}", teamId)
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
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("변경 후 소모임 이름"),
                                        fieldWithPath("introduction").description("변경 후 소모임 소개글"),
                                        fieldWithPath("profileImgUrl").description("변경 후 소모임 대표 사진")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("소모임을 수정했습니다."),
                                        fieldWithPath("data.teamId").description("수정한 teamId")
                                )
                        )
                );
    }

    @Test
    public void get_current_status() throws Exception {

        // given
        Long teamId = 1L;

        GetCurrentStatusResponse output = GetCurrentStatusResponse.builder()
                .name("팀 이름")
                .introduction("소개")
                .profileImgUrl("프로필 이미지")
                .build();

        given(getTeamUserCase.getCurrentStatus(any())).willReturn(output);


        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                get("/api/team/{teamId}", teamId)
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
                                pathParameters(
                                        parameterWithName("teamId").description("팀 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").description("true"),
                                        fieldWithPath("message").description("소모임을 수정했습니다."),
                                        fieldWithPath("data.name").description("소모임 이름"),
                                        fieldWithPath("data.introduction").description("소모임 소개글"),
                                        fieldWithPath("data.profileImgUrl").description("소모임 대표 사진")
                                )
                        )
                );
    }

}
