package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.service.Student;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class CsvStudents implements Students {

    /**
     * TODO 3 :
     * Java Singleton 패턴으로 getInstance() 를 구현하세요.
     **/

    private final Map<Integer, Student> students = new HashMap<>();

    // TODO 7 : student.csv 파일에서 데이터를 읽어 클래스 멤버 변수에 추가하는 로직을 구현하세요.
    // 데이터를 적재하고 읽기 위해서, 적절한 자료구조를 사용하세요.
    @Override
    public void load() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/data/student.csv"))))) {
            while (bufferedReader.ready()) {
                StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), ",");
                int seq = Integer.parseInt(tokenizer.nextToken());
                String name = tokenizer.nextToken();
                students.put(seq, new Student(seq, name));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Student> findAll() {
        return Collections.unmodifiableCollection(students.values());
    }

    /**
     * TODO 8 : students 데이터에 score 정보를 추가하세요.
     *
     * @param scores
     */
    @Override
    public void merge(Collection<Score> scores) {
        students.forEach((seq, student) -> {
            student.setScore(
                    scores.stream()
                            .filter(score -> score.getStudentSeq() == seq)
                            .findAny()
                            .orElseGet(() -> new Score(seq, 0)));
        });
    }
}
