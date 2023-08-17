//package com.moing.backend.domain.mission;
//
//import com.moing.backend.domain.mission.domain.entity.Mission;
//import com.moing.backend.domain.mission.domain.repository.MissionRepository;
//import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
//import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
//import com.moing.backend.domain.missionArchive.domain.entity.MissionArchive;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import javax.transaction.Transactional;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Rollback(value=false)
//@Transactional
//@SpringBootTest(properties = { "spring.config.location=classpath:application-test.yml" })
////@SpringBootTest
//public class MissionCreateTest {
//
//    @Autowired
//    private TeamRepository teamRepository;
//    @Autowired
//    private TeamMemberRepository teamMemberRepository;
//    @Autowired
//    private OnceMissionRepository onceMissionRepository;
//    @Autowired
//    private MissionRepository missionRepository;
//    @Autowired
//    private TestMemberRepository testMemberRepository;
//    @Autowired
//    private RepeatMissionRepository repeatMissionRepository;
//
//    @Test
//    public void 한번미션_생셩() {
//        Mission mission = new Mission();
//
//        Team makeTeam = Team.builder()
//                .name("hi")
//                .build();
//
////        teamRepository.save(makeTeam);
//
//        for (int i = 0; i < 3; i++) {
//            TestMember newMember = new TestMember("name"+i);
//            testMemberRepository.save(newMember);
//
//            TeamMember newTeams = TeamMember.builder()
//                    .member(newMember)
//                    .team(makeTeam)
//                    .build();
////            teamMemberRepository.save(newTeams);
//        }
////        assertThat(teamRepository.findAll().size()).isEqualTo(1);
////
//        List<MissionArchive> missionArchives = new ArrayList<>();
//
//        // fetch 주의
//        Team newTeam = teamRepository.findById(2L).get();
//        List<TeamMember> teamMembers = newTeam.getTeamMembers();
//
////        List<TeamMember> teamMembers = makeTeam.getTeamMembers();
//
////        assertThat(teamMembers.size()).isEqualTo(3);
//
//        for (TeamMember teamMember : teamMembers) {
//            MissionArchive newMissionArchive = MissionArchive.builder()
//                    .member(teamMember.getMember())
//                    .status(MissionStatus.WAITING)
//                    .build();
//
//            missionArchives.add(newMissionArchive);
//        }
//
//        OnceMission newOnceMission = OnceMission.builder()
//                .missionArchives(missionArchives)
//                .build();
////        onceMissionRepository.save(newOnceMission);
//
//        Mission newMission = Mission.builder()
//                .title("new")
//                .team(newTeam)
//                .dueTo(LocalDateTime.now())
//                .rule("rule")
//                .content("content")
//                .type(MissionType.ONCE)
//                .onceMission(newOnceMission)
//                .build();
//
//        missionRepository.save(newMission);
//
//        System.out.println("title : " + newMission.getTitle());
//        System.out.println("title : " + newMission.getRule());
//        System.out.println("user " + newMission.getTeam().getTeamMembers().size());
//
//    }
//
//    @Test
//    public void 반복미션_생성() {
//
//
//        Team makeTeam = Team.builder()
//                .name("hi")
//                .build();
//
//        teamRepository.save(makeTeam);
//
//
//        for (int i = 0; i < 3; i++) {
//            TestMember newMember = new TestMember("name"+i);
//            testMemberRepository.save(newMember);
//
//            TeamMember newTeams = TeamMember.builder()
//                    .member(newMember)
//                    .team(makeTeam)
//                    .build();
//        }
//
//        // 미션 생성
//
//        List<MissionArchive> missionArchives = new ArrayList<>();
//
//        // fetch 주의
//        Team newTeam = teamRepository.findById(2L).get();
//        List<TeamMember> teamMembers = newTeam.getTeamMembers();
//
//
//        for (TeamMember teamMember : teamMembers) {
//            MissionArchive newMissionArchive = MissionArchive.builder()
//                    .member(teamMember.getMember())
//                    .status(MissionStatus.WAITING)
//                    .build();
//
//            missionArchives.add(newMissionArchive);
//        }
//
//        OnceMission newOnceMission = OnceMission.builder()
//                .missionArchives(missionArchives)
//                .build();
//
//        onceMissionRepository.save(newOnceMission);
//        List<OnceMission> repeat = new ArrayList<>();
//
//        for (int i=0;i<2;i++){
//            repeat.add(newOnceMission);
//        }
//
//        RepeatMission save = repeatMissionRepository.save(RepeatMission.builder()
//                .times(2L)
//                .dayMissions(repeat)
//                .build());
//
//
//        Mission newMission = Mission.builder()
//                .title("new")
//                .team(newTeam)
//                .dueTo(LocalDateTime.now())
//                .rule("rule")
//                .content("content")
//                .type(MissionType.REPEAT)
//                .repeatMission(save)
//                .build();
//
//        missionRepository.save(newMission);
//
//        System.out.println("title : " + newMission.getTitle());
//        System.out.println("title : " + newMission.getRule());
//        System.out.println("user " + newMission.getTeam().getTeamMembers().size());
//        System.out.println("misison" + newMission.getRepeatMission().getDayMissions().size());
//    }
//}