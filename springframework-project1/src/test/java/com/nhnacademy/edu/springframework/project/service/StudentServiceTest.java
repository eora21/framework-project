package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import com.nhnacademy.edu.springframework.project.repository.Score;
import com.nhnacademy.edu.springframework.project.repository.StudentService;
import com.nhnacademy.edu.springframework.project.repository.Students;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class StudentServiceTest {

    private final StudentService studentService = new DefaultStudentService();

    @BeforeAll
    static void beforeAll() {
        Students students = CsvStudents.getInstance();

        List<Score> scores = List.of(
                new Score(1, 100),
                new Score(2, 90),
                new Score(3, 80),
                new Score(4, 70));

        Queue<Score> scoreQueue = new ArrayDeque<>(new ArrayList<>(scores));

        List<Student> studentList = List.of(
                new Student(1, "A"),
                new Student(2, "B"),
                new Student(3, "A"),
                new Student(4, "D"));

        studentList.forEach(student -> student.setScore(scoreQueue.remove()));

        students.load();
        students.merge(scores);
    }

    @Test
    @DisplayName("시험 통과 학생들")
    void getPassedStudents() {
        studentService.getPassedStudents().forEach(student -> {
            Assertions.assertTrue(60 < student.getScore().getScore());
        });
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