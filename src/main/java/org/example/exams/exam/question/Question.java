package org.example.exams.exam.question;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Question {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int type;
    @Getter
    @Setter
    private List<Integer> question;
    @Getter
    @Setter
    private List<Integer> partialScore;
    @Getter
    @Setter
    private String scoreMode;
    @Getter
    @Setter
    private int points;
    @Getter
    @Setter
    private int fixScore;

    public Question() {
        question = new ArrayList<>();
        partialScore = new ArrayList<>();
    }

    public void addQuestion(int q) {
        question.add(q);
    }

    public void addPartialScore(int s) {
        partialScore.add(s);
    }
}
