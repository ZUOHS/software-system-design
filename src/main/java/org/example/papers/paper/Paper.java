package org.example.papers.paper;

import lombok.Getter;
import lombok.Setter;
import org.example.papers.paper.answer.Answer;

import java.util.ArrayList;
import java.util.List;

public class Paper {

    @Getter
    @Setter
    private int examId;
    @Getter
    @Setter
    private int stuId;
    @Getter
    @Setter
    private Long submitTime;
    @Getter
    @Setter
    private List<Answer> answersList;

    public Paper() {
        answersList = new ArrayList<>();
    }

    public void addAnswer(Answer a) {
        answersList.add(a);
    }
}
