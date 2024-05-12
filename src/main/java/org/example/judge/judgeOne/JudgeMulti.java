package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.judge.judgeOne.multiStrategy.strategy;
import org.example.papers.paper.answer.Answer;

import java.util.List;
import java.util.Objects;

import static org.example.utils.Convert.convertStringToIntArrayList;

public class JudgeMulti implements JudgeOne {
    strategy strategy;

    public void setStrategy(strategy strategy) {
        this.strategy = strategy;
    }
    @Override
    public int judgeOne(Question q, List<Answer> an, String path) {
        List<Integer> left = q.getQuestion();
        for (Answer answer : an) {
            if (!answer.isValid()) {
                answer.setScore(0);
                continue;
            }

            List<Integer> right = convertStringToIntArrayList(answer.getAnswer());
            //首先把答案错误的情况挑出来

            boolean wrong = false;
            for (Integer integer : right) {
                for (int j = 0; j < left.size(); j++) {
                    if (Objects.equals(integer, left.get(j))) {
                        break;
                    } else if (j == left.size() - 1) {
                        answer.setScore(0);
                        wrong = true;
                    }
                }
            }
            if (wrong) {
                continue;
            }
            //然后判断是否全对
            //give people the benefit of the doubt
            boolean allCorrect = true;
            for (Integer integer : left) {
                for (int j = 0; j < right.size(); j++) {
                    if (Objects.equals(integer, right.get(j))) {
                        break;
                    } else if (j == right.size() - 1) {
                        allCorrect = false;
                    }
                }
            }
            if (allCorrect) {
                answer.setScore(q.getPoints());
            } else {
                answer.setScore(strategy.calPartial(q, answer));
            }
        }
        return 0;
    }

}
