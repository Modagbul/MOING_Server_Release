package com.moing.backend.domain.report.application.service;

import com.moing.backend.domain.board.application.dto.request.UpdateBoardRequest;
import com.moing.backend.domain.board.domain.entity.Board;
import com.moing.backend.domain.board.domain.service.BoardGetService;
import com.moing.backend.domain.boardComment.domain.entity.BoardComment;
import com.moing.backend.domain.boardComment.domain.service.BoardCommentGetService;
import com.moing.backend.domain.member.domain.service.MemberGetService;
import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.mission.domain.entity.constant.MissionWay;
import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
import com.moing.backend.domain.missionArchive.domain.entity.MissionArchiveStatus;
import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveQueryService;
import com.moing.backend.domain.report.application.mapper.ReportMapper;
import com.moing.backend.domain.report.domain.entity.Report;
import com.moing.backend.domain.report.domain.entity.constant.ReportType;
import com.moing.backend.domain.report.domain.service.ReportSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportCreateUseCase {

    private final ReportSaveService reportSaveService;
    private final MemberGetService memberGetService;

    private final BoardGetService boardGetService;
    private final MissionArchiveQueryService missionArchiveQueryService;
    private final BoardCommentGetService boardCommentGetService;

    private final String REPORT_BOARD_TITLE ="신고 접수된 게시물입니다.";
    private final String REPORT_BOARD_MESSAGE ="신고 접수로 삭제된 게시물입니다.";
    private final String REPORT_MISSION_MESSAGE ="신고 접수로 삭제된 인증입니다.";
    private final String REPORT_MISSION_PHOTO ="https://modagbul.s3.ap-northeast-2.amazonaws.com/reportImage.png";


    public Long createReport(String socialId, Long targetId, String reportType) {
        Long memberId = memberGetService.getMemberBySocialId(socialId).getMemberId();
        String targetMemberNickName = null ;

        if (reportType.equals(ReportType.BOARD.name())) {
            Board board = boardGetService.getBoard(targetId);

            targetMemberNickName = board.getTeamMember().getMember().getNickName();

            board.updateBoard(UpdateBoardRequest.builder()
                    .title(REPORT_BOARD_TITLE)
                    .content(REPORT_BOARD_MESSAGE)
                    .isNotice(board.isNotice())
                    .build());
        }
        else if (reportType.equals(ReportType.COMMENT.name())) {
            BoardComment boardComment = boardCommentGetService.getComment(targetId);
            targetMemberNickName = boardComment.getTeamMember().getMember().getNickName();
            boardComment.updateContent(REPORT_BOARD_MESSAGE);

        } else {

            MissionArchive missionArchive = missionArchiveQueryService.findByMissionArchiveId(targetId);
            Mission mission = missionArchive.getMission();

            targetMemberNickName = missionArchive.getMember().getNickName();

            if (mission.getWay().equals(MissionWay.PHOTO) && missionArchive.getStatus().equals(MissionArchiveStatus.COMPLETE)) {
                missionArchive.updateArchive(MissionArchiveReq.builder()
                        .archive(REPORT_MISSION_PHOTO)
                        .status(missionArchive.getStatus().name())
                        .build());
            } else {
                missionArchive.updateArchive(MissionArchiveReq.builder()
                        .archive(REPORT_MISSION_MESSAGE)
                        .status(missionArchive.getStatus().name())
                        .build());

            }

        }

        Report save = reportSaveService.save(ReportMapper.mapToReport(memberId, targetId, reportType,targetMemberNickName));

        return save.getTargetId();
    }
}
