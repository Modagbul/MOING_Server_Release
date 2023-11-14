//package com.moing.backend.domain.mission.application.service;
//
//import com.moing.backend.domain.mission.domain.entity.Mission;
//import com.moing.backend.domain.mission.domain.entity.constant.MissionStatus;
//import com.moing.backend.domain.mission.domain.entity.constant.MissionType;
//import com.moing.backend.domain.missionState.application.service.MissionStateScheduleUseCase;
//import com.moing.backend.domain.missionState.domain.entity.MissionState;
//import com.moing.backend.domain.missionState.domain.service.MissionStateDeleteService;
//import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.*;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.batch.item.database.JpaPagingItemReader;
//import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
//import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.persistence.EntityManagerFactory;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import static com.moing.backend.domain.mission.domain.entity.QMission.mission;
//
//@Slf4j
//@Configuration
//@EnableBatchProcessing
//@RequiredArgsConstructor
//public class MissionTerminateBatchConfig {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final EntityManagerFactory entityManagerFactory;
//
//    private final TeamScoreLogicUseCase teamScoreLogicUseCase;
//    private final MissionStateDeleteService missionStateDeleteService;
//    private final MissionStateScheduleUseCase missionStateScheduleUseCase;
//
//    @Bean
//    public Job missionTerminateJob() {
//        return jobBuilderFactory.get("missionTerminateJob")
//                .start(missionTerminateStep())
//                .build();
//    }
//
//    @Bean
//    public Step missionTerminateStep() {
//        return stepBuilderFactory.get("missionTerminateStep")
//                .<Mission, Mission>chunk(10)
//                .reader(missionReader())
//                .processor(missionProcessor())
//                .writer(missionWriter())
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public JpaPagingItemReader<Mission> missionReader() {
//        return new JpaPagingItemReaderBuilder<Mission>()
//                .name("missionReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT m FROM Mission m WHERE m.status = 'ONGOING' AND m.type = 'REPEAT'")
//                .pageSize(10)
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Mission, Mission> missionProcessor() {
//        return mission -> {
//            teamScoreLogicUseCase.updateTeamScore(mission.getId());
//            return mission;
//        };
//    }
//
//
//
//    @Bean
//    public JpaItemWriter<Mission> teamScoreWriter() {
//        missionStateScheduleUseCase.missionStateReset();
//        return new JpaItemWriterBuilder<Mission>()
//                .entityManagerFactory(entityManagerFactory)
//                .build();
//    }
//
//
//
//
//
//}
