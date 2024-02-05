package com.moing.backend.domain.board.domain;

import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.GetAllBoardResponse;
import com.moing.backend.domain.board.application.mapper.BoardMapper;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.repository.BoardRepository;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.boardRead.domain.repository.BoardReadRepository;
import com.moing.backend.domain.member.domain.constant.Gender;
import com.moing.backend.domain.member.domain.constant.RegistrationStatus;
import com.moing.backend.domain.member.domain.constant.Role;
import com.moing.backend.domain.member.domain.constant.SocialProvider;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.member.domain.repository.MemberRepository;
import com.moing.backend.domain.team.domain.constant.ApprovalStatus;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.team.domain.repository.TeamRepository;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.domain.teamMember.domain.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
@Transactional
public class BoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardReadRepository boardReadRepository;

    private Member minsu, seungyeon;

    private Team team;
    private TeamMember deletedTM, validTM;
    private CreateBoardRequest createBoardRequest;

    @BeforeEach
    void setUp() {
        //given
        minsu = memberRepository.save(new Member(null, "minsu@naver.com", "undef", Gender.WOMAN, null, "민수", null, SocialProvider.KAKAO, RegistrationStatus.COMPLETED, Role.USER, "KAKAO@minsu"));
        seungyeon = memberRepository.save(new Member(null, "seungyeon@naver.com", "undef", Gender.WOMAN, null, "승연", null, SocialProvider.KAKAO, RegistrationStatus.COMPLETED, Role.USER, "KAKAO@seungyeon"));

        team = teamRepository.save(Team.builder()
                .category("ETC")
                .name("소모임 이름")
                .introduction("소모임 소개")
                .promise("소모임 각오")
                .profileImgUrl("소모임 프로필 이미지 url")
                .approvalStatus(ApprovalStatus.APPROVAL)
                .leaderId(1L)
                .numOfMember(2)
                .levelOfFire(1)
                .build());

        validTM=teamMemberRepository.save(TeamMember.builder().team(team).member(minsu).isDeleted(false).build());
        deletedTM=teamMemberRepository.save(TeamMember.builder().team(team).member(seungyeon).isDeleted(true).build());

        createBoardRequest = CreateBoardRequest.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .isNotice(false)
                .build();
    }

    @Test
    @DisplayName("게시글을 읽은 경우")
    void whenBoardIsRead_thenMarkAsRead() {
        //given
        Board board = boardRepository.save(BoardMapper.toBoard(validTM, team, createBoardRequest, false));

        boardReadRepository.save(new BoardRead(null, board, team, minsu));

        //when
        GetAllBoardResponse response = boardRepository.findBoardAll(team.getTeamId(), minsu.getMemberId());

        //then
        assertThat(response.getNotNoticeBlocks().get(0).getIsRead()).isTrue();
    }

    @Test
    @DisplayName("게시글을 읽지 않은 경우")
    void whenBoardIsNotRead_thenMarkAsUnread() {
        //given
        Board board = boardRepository.save(BoardMapper.toBoard(validTM, team, createBoardRequest, false));
        
        //when
        GetAllBoardResponse response = boardRepository.findBoardAll(team.getTeamId(), minsu.getMemberId());

        //then
        assertThat(response.getNotNoticeBlocks().get(0).getIsRead()).isFalse();
    }
    @Test
    @DisplayName("작성자가 삭제된 경우")
    void 게시글_작성자가_탈퇴하지_않은_경우_게시글_전체_조회() {
        //given
        Board board = boardRepository.save(BoardMapper.toBoard(deletedTM, team, createBoardRequest, false)); //작성자 탈퇴한 경우

        //when
        GetAllBoardResponse response=boardRepository.findBoardAll(team.getTeamId(),minsu.getMemberId());

        //then
        assertThat(response.getNotNoticeBlocks().get(0).getWriterNickName()).isEqualTo("(알 수 없음)");
    }
}
