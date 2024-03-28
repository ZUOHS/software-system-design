package org.example.exams.exam;

import lombok.Getter;
import lombok.Setter;
import org.example.exams.exam.question.Question;

import java.util.ArrayList;
import java.util.List;

public class Exam {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private Long startTime;
    @Getter
    @Setter
    private Long endTime;
    @Getter
    @Setter
    private List<Question> questions;

    public Exam() {
        questions = new ArrayList<>();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

}
