package com.moing.backend.domain.board.application.mapper;

import com.moing.backend.domain.board.application.dto.request.CreateBoardRequest;
import com.moing.backend.domain.board.application.dto.response.GetBoardDetailResponse;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class BoardMapper {

    public Board toBoard(TeamMember teamMember, Team team, CreateBoardRequest createBoardRequest, boolean isLeader) {
        Board board = Board.builder()
                .title(createBoardRequest.getTitle())
                .content(createBoardRequest.getContent())
                .isNotice(createBoardRequest.getIsNotice())
                .commentNum(0)
                .isLeader(isLeader)
                .boardReads(new ArrayList<>())
                .boardComments(new ArrayList<>())
                .build();
        board.updateTeamMember(teamMember);
        board.updateTeam(team);
        return board;
    }

    public GetBoardDetailResponse toBoardDetail(Board board, boolean isWriter, boolean writerIsDeleted) {
        String nickName = writerIsDeleted ? "(알 수 없음)" : board.getTeamMember().getMember().getNickName();
        String writerProfileImage = writerIsDeleted ? null : board.getTeamMember().getMember().getProfileImage();
        Long writerId = writerIsDeleted ? 0L : board.getTeamMember().getMember().getMemberId();
        return GetBoardDetailResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerNickName(nickName)
                .writerIsLeader(board.isLeader())
                .writerProfileImage(writerProfileImage)
                .createdDate(getFormattedDate(board.getCreatedDate()))
                .isWriter(isWriter)
                .isNotice(board.isNotice())
                .makerId(writerId)
                .build();
    }

    public String getFormattedDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return localDateTime.format(formatter);
    }

}
