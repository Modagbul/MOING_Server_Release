package com.moing.backend.domain.mission.application.service;

import com.moing.backend.domain.mission.domain.entity.Mission;
import com.moing.backend.domain.missionState.application.service.MissionStateUseCase;
import com.moing.backend.domain.teamScore.application.service.TeamScoreLogicUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RepeatMissionEnd {

    public static final String JOB_NAME = "makeMissionEnd";

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;

    private final MissionStateUseCase missionStateUseCase;
    private final TeamScoreLogicUseCase teamScoreLogicUseCase;

    private final int chunkSize = 10;

    @Bean
    public Job payPagingJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(payPagingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step payPagingStep() {
        return stepBuilderFactory.get("makeMissionEndStep")
                .<Mission, Mission>chunk(chunkSize)
                .reader(payPagingReader())
                .processor(payPagingProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Mission> payPagingReader() {

        JpaPagingItemReader<Mission> reader = new JpaPagingItemReader<Mission>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT m FROM Mission m WHERE m.status = 'ONGOING' AND m.type = 'REPEAT'");
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("payPagingReader");

        return reader;
    }

    // sundayRepeatMissionRoutine()
    @Bean
    @StepScope
    public ItemProcessor<Mission, Mission> payPagingProcessor() {
        return mission -> {
            teamScoreLogicUseCase.updateTeamScore(mission.getId());
            missionStateUseCase.deleteAllMissionState(mission.getId());
            mission.makeEnd();
            return mission;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Mission> writer() {
        JpaItemWriter<Mission> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}