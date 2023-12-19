//package com.moing.backend.domain.missionArchive.domain.repository;
//
//import com.moing.backend.domain.history.application.dto.response.MemberIdAndToken;
//import com.moing.backend.domain.mission.application.dto.res.SingleMissionBoardRes;
//import com.moing.backend.domain.mission.application.service.MissionRemindAlarmUseCase;
//import com.moing.backend.domain.mission.domain.entity.Mission;
//import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
//import com.moing.backend.domain.mission.domain.repository.MissionRepository;
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
//
//    @Test
//    void findPeopleRemainMission() {
//
//        //System.out.println("!!!"+ missionArchiveRepository.findHavingRemainMissions().get().size());
//
//        List<MemberIdAndToken> remainMissionPeople = missionArchiveScheduleQueryService.getRemainMissionPeople().orElseThrow();
//        for (MemberIdAndToken remainMissionPerson : remainMissionPeople) {
//            System.out.println("!!"+remainMissionPerson.getFcmToken() + " " + remainMissionPerson.getMemberId());
//
//        }
//        missionRemindAlarmUseCase.sendRemindMissionAlarm();
//    }
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
//}