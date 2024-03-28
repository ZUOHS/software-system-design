package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.judge.JudgeOne;
import org.example.papers.paper.answer.Answer;

public class JudgeCode implements JudgeOne {
    @Override
    public int judgeOne(Question q, Answer an) {
        return q.getPoints();
    }

}
