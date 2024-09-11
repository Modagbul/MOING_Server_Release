//package com.moing.backend.domain.missionArchive.domain.repository;
//
//import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
//import com.moing.backend.domain.member.domain.entity.Member;
//import com.moing.backend.domain.mission.application.dto.res.FinishMissionBoardRes;
//import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
//import com.moing.backend.domain.mission.application.service.MissionRemindAlarmUseCase;
//import com.moing.backend.domain.mission.domain.entity.Mission;
//import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
//import com.moing.backend.domain.mission.domain.repository.MissionRepository;
//import com.moing.backend.domain.missionArchive.application.dto.req.MissionArchiveReq;
//import com.moing.backend.domain.missionArchive.application.dto.res.MissionArchiveRes;
//import com.moing.backend.domain.missionArchive.application.dto.res.MyArchiveStatus;
//import com.moing.backend.domain.missionArchive.application.dto.res.PersonalArchiveRes;
//import com.moing.backend.domain.missionArchive.application.mapper.MissionArchiveMapper;
//import com.moing.backend.domain.missionArchive.application.service.MissionArchiveCreateUseCase;
//import com.moing.backend.domain.missionArchive.application.service.MissionArchiveReadUseCase;
//import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
//import com.moing.backend.domain.missionArchive.domain.service.MissionArchiveScheduleQueryService;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//import java.util.Optional;
//
//import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
//import static com.moing.backend.domain.missionState.domain.entity.QMissionState.missionState;
//@SpringBootTest
//
//class MissionArchiveCustomRepositoryImplTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    private MissionArchiveRepository missionArchiveRepository;
//
//    @Autowired
//    private MissionRepository missionRepository;
//
//    @Autowired
//    MissionRemindAlarmUseCase missionRemindAlarmUseCase;
//
//    @Autowired
//    MissionArchiveScheduleQueryService missionArchiveScheduleQueryService;
//
//    @Autowired
//    MissionArchiveCreateUseCase missionArchiveCreateUseCase;
//
//    @Autowired
//    MissionArchiveReadUseCase missionArchiveReadUseCase;
//
//
//
//
//    @Test
//    void findEndMission() {
//        List<Mission> missionByDueTo = missionRepository.findMissionByDueTo().orElseThrow();
//        for (Mission mission1 : missionByDueTo) {
//            System.out.println(mission1.getTitle()+ mission1.getStatus());
//            mission1.updateStatus(MissionStatus.END);
//            System.out.println(mission1.getStatus());
//        }
//    }
//
//
//    @Test
//    void findInCompleteMission() {
//        List<SingleMissionBoardRes> singleMissionBoardResList = missionArchiveRepository.findSingleMissionInComplete(33L, 48L, MissionStatus.ONGOING, OrderCondition.DUETO).orElseThrow();
//        for (SingleMissionBoardRes singleMissionBoardRes : singleMissionBoardResList) {
//            System.out.println(singleMissionBoardRes.getTitle());
//        }
//    }
//
//    @Test
//    void findMissionStatus() {
//        MissionArchive missionArchive = missionArchiveRepository.findById(198L).orElseThrow();
//        MissionArchiveRes missionArchiveRes = MissionArchiveMapper.mapToMissionArchiveRes(missionArchive,33L);
//        System.out.println(missionArchiveRes.toString());
//    }
//
//    @Test
//    void createMissionArchive() {
//        System.out.println(missionArchiveCreateUseCase.createArchive("KAKAO@tester02", 211L, MissionArchiveReq.builder()
//                .status("SKIP")
//                .archive("hihi")
//                .build()));
//
//        System.out.println();
//
//
//    }
//
//
//    @Test
//    void findRemainPeople() {
//
//        List<Member> members = missionRepository.findRepeatMissionPeopleByStatus(MissionStatus.WAIT).orElseThrow();
//        for (Member member : members) {
//            System.out.println(member.getNickName());
//        }    }
//
////        @Test
////    void report() {
////
////        reportCreateUseCase.createReport("KAKAO@tester02", 550L, "MISSION");
////
////        List<Long> teams = new ArrayList<>();
////        teams.add(48L);
////        List<MissionArchivePhotoRes> top5ArchivesByTeam = missionArchiveRepository.findTop5ArchivesByTeam(teams).orElseThrow();
////
////        System.out.println(top5ArchivesByTeam.get(0).getPhoto().get(0).toString());
////
////    }
//
//
//    @Test
//    void catHeartList() {
//
//        List<PersonalArchiveRes> personalArchive = missionArchiveReadUseCase.getPersonalArchive("APPLE@001616.4e6d97f481fa441aa3a6169666c5552a.0841", 234L);
//        for (PersonalArchiveRes personalArchiveRes : personalArchive) {
//            System.out.println(personalArchiveRes);
//        }
//    }
//
//    @Test
//    void finishiMissions() {
//        List<FinishMissionBoardRes> finishMissionBoardResList = missionArchiveRepository.findFinishMissionsByStatus(33L, 48L).orElseThrow();
//        for (FinishMissionBoardRes finishMissionBoardRes : finishMissionBoardResList) {
//            System.out.println(finishMissionBoardRes.toString());
//        }
//    }
//
//    @Test
//    void finishArchives() {
//        List<MissionArchive> missionArchives = missionArchiveRepository.findMyArchives(33L, 323L).orElseThrow();
//        for (MissionArchive missionArchive : missionArchives) {
//            System.out.println(missionArchive.toString());
//        }
//    }
//
//    @Test
//    void isstatus() {
//
//        MyArchiveStatus missionStatusById = missionArchiveRepository.findMissionStatusById(51L, 323L, 48L);
//        System.out.println(missionStatusById);
//    }
//}