package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.config.SpringBeanConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBeanConfig.class)
class ScoresTest {

    @Autowired
    private Scores scores;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(scores, "scores", new ArrayList<>());
    }

    @Test
    @DisplayName("로드 이전, 이후 사이즈 확인")
    void load() {
        Assertions.assertEquals(0, scores.findAll().size());
        scores.load();
        Assertions.assertEquals(3, scores.findAll().size());
    }

    @Test
    @DisplayName("리스트 검증")
    void findAll() {
        Queue<Score> queue = new ArrayDeque<>(
                List.of(new Score(1, 30),
                        new Score(2, 80),
                        new Score(3, 70)));

        scores.load();
        scores.findAll().forEach(score -> {
            Score testScore = queue.remove();
            Assertions.assertEquals(score.getStudentSeq(), testScore.getStudentSeq());
            Assertions.assertEquals(score.getScore(), testScore.getScore());
        });
    }
}