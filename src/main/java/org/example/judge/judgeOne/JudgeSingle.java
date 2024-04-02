package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.papers.paper.answer.Answer;

import java.util.List;

import static org.example.utils.Convert.convertStringToIntArrayList;

public class JudgeSingle implements JudgeOne {
    @Override
    public int judgeOne(Question q, List<Answer> an, String path) {
        int left = q.getQuestion().get(0);
        for (Answer answer : an) {
            int right = convertStringToIntArrayList(answer.getAnswer()).get(0);
            if (left == right) {
                answer.setScore(q.getPoints());
            } else {
                answer.setScore(0);
            }
        }
        return 0;
    }

}
