package org.example.papers.paper;

import lombok.Data;
import org.example.papers.paper.answer.Answer;

import java.util.ArrayList;
import java.util.List;

@Data
public class Paper {
    private int examId;
    private int stuId;
    private Long submitTime;
    private List<Answer> answersList;

    public Paper() {
        answersList = new ArrayList<>();
    }

    public void addAnswer(Answer a) {
        answersList.add(a);
    }
}
