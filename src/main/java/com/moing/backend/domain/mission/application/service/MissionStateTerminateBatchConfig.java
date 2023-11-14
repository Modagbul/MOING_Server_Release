//package com.moing.backend.domain.mission.application.service;
//
//import com.moing.backend.domain.mission.domain.entity.Mission;
//import com.moing.backend.domain.missionState.domain.entity.MissionState;
//import com.moing.backend.domain.missionState.domain.service.MissionStateDeleteService;
//import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
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
//
//@Slf4j
//@Configuration
//@EnableBatchProcessing
//@RequiredArgsConstructor
//public class MissionStateTerminateBatchConfig {
//
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//    private final EntityManagerFactory entityManagerFactory;
//
//    private final TeamScoreLogicUseCase teamScoreLogicUseCase;
//    private final MissionStateDeleteService missionStateDeleteService;
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
//                .reader(missionStateReader())
//                .processor(missionStateProcessor())
//                .writer(missionStateWriter())
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public JpaPagingItemReader<Mission,MissionState> missionStateReader() {
//
//        Map<String,Object> parameterValues = new HashMap<>();
//        parameterValues.put("missionId", 10000);
//
//                        .parameterValues(parameterValues) // 쿼리 파라미터 설정
//
//        return new JpaPagingItemReaderBuilder<MissionState>()
//                .name("missionStateReader")
//                .entityManagerFactory(entityManagerFactory)
//                .queryString("SELECT m FROM MissionState m WHERE m.mission_id = :missionId")
//                .pageSize(10)
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<Mission, Mission> missionStateProcessor() {
//        return mission -> {
//            teamScoreLogicUseCase.updateTeamScore(mission.getId());
//            return mission;
//        };
//    }
//
//
//
//    @Bean
//    public JpaItemWriter<Mission> missionStateWriter() {
//        return new JpaItemWriterBuilder<Mission>()
//                .entityManagerFactory(entityManagerFactory)
//                .build();
//    }
//
//
//
//}
