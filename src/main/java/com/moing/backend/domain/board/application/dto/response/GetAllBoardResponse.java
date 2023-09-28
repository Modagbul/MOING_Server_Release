package com.moing.backend.domain.board.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class GetAllBoardResponse {
    private Long noticeNum;
    private List<BoardBlocks> notices=new ArrayList<>();
    private Long notNoticeNum;
    private List<BoardBlocks> notNotices=new ArrayList<>();
}
