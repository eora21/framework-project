package com.nhnacademy.edu.springframework.project.repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CsvScores implements Scores {

    private CsvScores() {
    }

    /**
     * TODO 2 :
     * Java Singleton 패턴으로 getInstance() 를 구현하세요.
     **/

    private static final CsvScores INSTANCE = new CsvScores();
    private final List<Score> scores = new ArrayList<>();

    public static Scores getInstance() {
        return INSTANCE;
    }

    // TODO 5 : score.csv 파일에서 데이터를 읽어 멤버 변수에 추가하는 로직을 구현하세요.
    @Override
    public void load() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/data/score.csv"))))) {
            while (bufferedReader.ready()) {
                StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), ",");
                scores.add(
                        new Score(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Score> findAll() {
        return Collections.unmodifiableList(scores);
    }
}
