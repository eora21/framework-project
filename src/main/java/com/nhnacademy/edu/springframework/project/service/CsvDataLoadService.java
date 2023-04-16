package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.repository.CsvScores;
import com.nhnacademy.edu.springframework.project.repository.CsvStudents;
import com.nhnacademy.edu.springframework.project.repository.Scores;
import com.nhnacademy.edu.springframework.project.repository.Students;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsvDataLoadService implements DataLoadService {

    private final Scores scores;
    private final Students students;

    @Override
    public void loadAndMerge() {
        scores.load();
        students.load();
        students.merge(scores.findAll());
    }
}
