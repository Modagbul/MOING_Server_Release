package com.moing.backend.domain.missionComment.domain;

import com.moing.backend.domain.missionComment.domain.repository.MissionCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")
@Transactional
public class MissionRepositoryTest {

    @Autowired
    MissionCommentRepository missionCommentRepository;
    public void create_Mission_Comment(){

    }

}
