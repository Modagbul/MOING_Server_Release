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
    private int noticeNum;
    private List<BoardBlocks> noticeBlocks=new ArrayList<>();
    private int notNoticeNum;
    private List<BoardBlocks> notNoticeBlocks=new ArrayList<>();
}
