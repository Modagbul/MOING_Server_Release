package com.moing.backend.domain.missionHeart.presentation;

import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
import com.moing.backend.domain.missionHeart.application.dto.MissionHeartRes;
import com.moing.backend.domain.missionHeart.application.service.MissionHeartUseCase;
import com.moing.backend.global.config.security.dto.User;
import com.moing.backend.global.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.moing.backend.domain.missionArchive.domain.constant.MissionArchiveResponseMessage.CREATE_ARCHIVE_SUCCESS;

@RestController
@AllArgsConstructor
@RequestMapping("/api/{teamId}/missions/{missionId}/archive/{archiveId}/heart")
public class MissionHeartController {


}
