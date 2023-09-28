package com.moing.backend.domain.board.domain.entity;

import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.boardRead.domain.entity.BoardRead;
import com.moing.backend.domain.team.domain.entity.Team;
import com.moing.backend.domain.teamMember.domain.entity.TeamMember;
import com.moing.backend.global.entity.BaseTimeEntity;
import com.moing.backend.global.util.AesConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    //반정규화 -> 작성자에 대한 정보
    @Convert(converter = AesConverter.class)
    @Column(nullable = false)
    private String writerNickName;

    private boolean isLeader;

    private String writerProfileImage;

    @Column(nullable = false, length = 15)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    private boolean isNotice;

    //반정규화 -> 댓글 개수
    private Integer commentNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id")
    private TeamMember teamMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardRead> boardReads = new ArrayList<>();


    public void incrComNum() {
        this.commentNum++;
    }

    public void decrComNum() {
        this.commentNum--;
    }

    public void updateBoard(UpdateBoardRequest updateBoardRequest){
        this.title= updateBoardRequest.getTitle();
        this.content= updateBoardRequest.getContent();
        this.isNotice= updateBoardRequest.getIsNotice();
    }

    //==연관관계 메서드 ==//
    public void updateTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    public void updateTeam(Team team) {
        this.team = team;
    }
}
