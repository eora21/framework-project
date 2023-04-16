package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.config.SpringBeanConfig;
import com.nhnacademy.edu.springframework.project.repository.StudentService;
import com.nhnacademy.edu.springframework.project.repository.Students;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBeanConfig.class)
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private Students students;

    @BeforeAll
    void beforeAll() {
        ReflectionTestUtils.setField(students, "students", new HashMap<>());
    }

    @Test
    @DisplayName("시험 통과 학생들")
    void getPassedStudents() {
        studentService.getPassedStudents().forEach(student ->
                Assertions.assertTrue(60 < student.getScore().getScore()));
    }

    @Test
    @DisplayName("시험점수 내림차순")
    void getStudentsOrderByScore() {
        int minScore = studentService.getStudentsOrderByScore().stream()
                .mapToInt(student -> student.getScore().getScore())
                .reduce(Integer.MAX_VALUE, (maxVal, score) -> {
                    Assertions.assertTrue(maxVal > score);
                    return score;
                });

        Assertions.assertTrue(0 <= minScore);
    }
}