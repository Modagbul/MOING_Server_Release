package com.moing.backend.domain.boardComment.domain.entity;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long boardCommentId;

    @Column(nullable = false, length = 300)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private boolean isLeader; /*작성자 소모임장유무*/

    /**
     * 연관관계 매핑
     */
    public void updateBoard(Board board) {
        this.board = board;
        board.getBoardComments().add(this);
    }

    public void updateTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

}
