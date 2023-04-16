package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.config.SpringBeanConfig;
import com.nhnacademy.edu.springframework.project.repository.Score;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBeanConfig.class)
class GradeQueryServiceTest {

    @Autowired
    private GradeQueryService gradeQueryService;

    @Autowired
    private DataLoadService dataLoadService;

    @BeforeAll
    void beforeAll() {
        dataLoadService.loadAndMerge();
    }

    @Test
    @DisplayName("학생 이름으로 점수 확인")
    void getScoreByStudentName() {
        Queue<Score> scoreQueue = new ArrayDeque<>(List.of(
                new Score(1, 30),
                new Score(3, 70)));

        gradeQueryService.getScoreByStudentName("A").forEach(score -> {
            Score testScore = scoreQueue.remove();
            Assertions.assertEquals(score.getStudentSeq(), testScore.getStudentSeq());
            Assertions.assertEquals(score.getScore(), testScore.getScore());
        });
    }

    @Test
    @DisplayName("학번으로 점수 확인")
    void getScoreByStudentSeq() {
        Score score = gradeQueryService.getScoreByStudentSeq(1);
        Score testScore = new Score(1, 30);

        Assertions.assertEquals(score.getStudentSeq(), testScore.getStudentSeq());
        Assertions.assertEquals(score.getScore(), testScore.getScore());
    }
}