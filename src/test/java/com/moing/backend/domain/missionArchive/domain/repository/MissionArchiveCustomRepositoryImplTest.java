//package com.moing.backend.domain.missionArchive.domain.repository;
//
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
//    @Test
//    void findPeopleRemainMission() {
//
//        System.out.println("!!!"+ missionArchiveRepository.findPeopleRemainMission());
//
//    }
//}