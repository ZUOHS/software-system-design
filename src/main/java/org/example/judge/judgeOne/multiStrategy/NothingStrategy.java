package org.example.judge.judgeOne.multiStrategy;

import org.example.exams.exam.question.Question;
import org.example.papers.paper.answer.Answer;

public class NothingStrategy implements strategy {
    @Override
    public int calPartial(Question q, Answer an) {
        return 0;
    }
}
