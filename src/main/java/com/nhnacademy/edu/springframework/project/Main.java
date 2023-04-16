package com.nhnacademy.edu.springframework.project;

import com.nhnacademy.edu.springframework.project.service.CsvDataLoadService;
import com.nhnacademy.edu.springframework.project.service.DataLoadService;
import com.nhnacademy.edu.springframework.project.service.DefaultStudentService;
import com.nhnacademy.edu.springframework.project.service.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;

@Slf4j
public class Main {

    // TODO 9 - 성공적으로 실행되어야 합니다.
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.nhnacademy.edu.springframework.project.config")) {
            DataLoadService dataLoadService = context.getBean(CsvDataLoadService.class);
            dataLoadService.loadAndMerge();

            DefaultStudentService studentService = context.getBean(DefaultStudentService.class);
            Collection<Student> passedStudents = studentService.getPassedStudents();
            log.info("passStudents: {}", passedStudents);

            Collection<Student> orderedStudents = studentService.getStudentsOrderByScore();
            log.info("orderByScore: {}", orderedStudents);
        }
    }
}
