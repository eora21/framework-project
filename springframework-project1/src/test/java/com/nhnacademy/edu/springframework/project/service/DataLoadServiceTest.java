package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.CsvScores;
import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import com.nhnacademy.edu.springframework.project.repository.Scores;
import com.nhnacademy.edu.springframework.project.repository.Students;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataLoadServiceTest {

    private final DataLoadService dataLoadService = new CsvDataLoadService();

    @Test
    @DisplayName("로드 후 머지 확인")
    void loadAndMerge() {
        Scores scores = CsvScores.getInstance();
        Students students = CsvStudents.getInstance();

        Assertions.assertEquals(0, scores.findAll().size());
        Assertions.assertEquals(0, students.findAll().size());

        dataLoadService.loadAndMerge();

        Assertions.assertEquals(3, scores.findAll().size());
        Assertions.assertEquals(4, students.findAll().size());
    }
}