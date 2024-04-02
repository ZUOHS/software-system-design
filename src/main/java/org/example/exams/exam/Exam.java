package org.example.exams.exam;

import lombok.Data;
import org.example.exams.exam.question.Question;

import java.util.ArrayList;
import java.util.List;

@Data
public class Exam {
    private int id;
    private String title;
    private Long startTime;
    private Long endTime;
    private List<Question> questions;

    public Exam() {
        questions = new ArrayList<>();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

}
