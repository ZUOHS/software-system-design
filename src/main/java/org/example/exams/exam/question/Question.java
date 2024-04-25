package org.example.exams.exam.question;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {
    private int id;
    private int type;
    private List<Integer> question;
    private List<Integer> partialScore;
    private String scoreMode;
    private int timeLimit;
    private int points;
    private int fixScore;
    private List<String []> samples;

    public Question() {
        question = new ArrayList<>();
        partialScore = new ArrayList<>();
        samples = new ArrayList<>();
    }

    public void addQuestion(int q) {
        question.add(q);
    }

    public void addPartialScore(int s) {
        partialScore.add(s);
    }

    public void addSample(String input, String output) {
        samples.add(new String[]{input, output});
    }
}
