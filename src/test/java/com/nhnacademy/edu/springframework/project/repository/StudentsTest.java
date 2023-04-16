package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.config.SpringBeanConfig;
import com.nhnacademy.edu.springframework.project.service.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringBeanConfig.class)
class StudentsTest {

    @Autowired
    private Students students;
    
    @BeforeEach
    void beforeEach() {
        ReflectionTestUtils.setField(students, "students", new HashMap<>());
    }

    @Test
    @DisplayName("로드 이전, 이후 사이즈 확인")
    void load() {
        Assertions.assertEquals(0, students.findAll().size());
        students.load();
        Assertions.assertEquals(4, students.findAll().size());
    }

    @Test
    @DisplayName("리스트 검증")
    void findAll() {
        Queue<Student> queue = new ArrayDeque<>(
                List.of(new Student(1, "A"),
                        new Student(2, "B"),
                        new Student(3, "A"),
                        new Student(4, "D")));

        students.load();
        students.findAll().forEach(student -> {
            Student testStudent = queue.remove();
            Assertions.assertEquals(student.getSeq(), testStudent.getSeq());
            Assertions.assertEquals(student.getName(), testStudent.getName());
        });
    }

    @Test
    @DisplayName("머지 검증")
    void merge() {
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
        Queue<Student> studentQueue = new ArrayDeque<>(studentList);

        students.load();
        students.merge(scores);

        students.findAll().forEach(student -> {
            Student testStudent = studentQueue.remove();

            Assertions.assertEquals(student.getSeq(), testStudent.getSeq());
            Assertions.assertEquals(student.getName(), testStudent.getName());
        });
    }
}