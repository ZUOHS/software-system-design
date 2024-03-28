package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.judge.JudgeOne;
import org.example.papers.paper.answer.Answer;

import static org.example.utils.Convert.convertStringToIntArrayList;

public class JudgeSingle implements JudgeOne {
    @Override
    public int judgeOne(Question q, Answer an) {
        int left = q.getQuestion().get(0);
        int right = convertStringToIntArrayList(an.getAnswer()).get(0);
        return left == right ? q.getPoints() : 0;
    }

}
