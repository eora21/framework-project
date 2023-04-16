package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.config.SpringBeanConfig;
import com.nhnacademy.edu.springframework.project.repository.Scores;
import com.nhnacademy.edu.springframework.project.repository.Students;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBeanConfig.class)
class DataLoadServiceTest {

    @Autowired
    private DataLoadService dataLoadService;

    @Autowired
    private Scores scores;

    @Autowired
    private Students students;

    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(students, "students", new HashMap<>());
        ReflectionTestUtils.setField(scores, "scores", new ArrayList<>());
    }

    @Test
    @DisplayName("로드 후 머지 확인")
    void loadAndMerge() {
        Assertions.assertEquals(0, scores.findAll().size());
        Assertions.assertEquals(0, students.findAll().size());

        dataLoadService.loadAndMerge();

        Assertions.assertEquals(3, scores.findAll().size());
        Assertions.assertEquals(4, students.findAll().size());
    }
}