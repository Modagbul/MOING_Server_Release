package com.moing.backend.domain.boardRead.domain.entity;

import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.member.domain.entity.Member;
import com.moing.backend.domain.team.domain.entity.Team;
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
public class BoardRead extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_read_id")
    private Long boardReadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team__id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_id")
    private Member member;


    /**
     * 연관관계 매핑
     */
    public void updateBoard(Board board) {
        this.board = board;
        board.getBoardReads().add(this);
    }

    public void updateTeam(Team team) {
        this.team = team;
    }

    public void updateMember(Member member) {
        this.member = member;
    }
}
