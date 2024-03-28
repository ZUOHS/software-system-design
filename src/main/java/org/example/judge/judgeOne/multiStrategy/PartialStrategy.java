package org.example.judge.judgeOne.multiStrategy;

import org.example.exams.exam.question.Question;
import org.example.papers.paper.answer.Answer;

import java.util.List;

import static org.example.utils.Convert.convertStringToIntArrayList;

public class PartialStrategy implements strategy {

    @Override
    public int calPartial(Question q, Answer an) {
        List<Integer> left = q.getQuestion();
        List<Integer> right = convertStringToIntArrayList(an.getAnswer());
        List<Integer> score = q.getPartialScore();
        int sum = 0;
        for (Integer integer : right) {
            for (int j = 0; j < left.size(); j++) {
                if (integer.equals(left.get(j))) {
                    sum += score.get(j);
                    break;
                }
            }
        }
        return sum;
    }
}
