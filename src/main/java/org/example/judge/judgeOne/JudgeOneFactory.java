package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.judge.judgeOne.multiStrategy.FixStrategy;
import org.example.judge.judgeOne.multiStrategy.NothingStrategy;
import org.example.judge.judgeOne.multiStrategy.PartialStrategy;

import java.util.Objects;

public class JudgeOneFactory {
    public static JudgeOne getJudgeOne(Question question) {
        if (question.getType() == 1) {
            return new JudgeSingle();
        } else if (question.getType() == 3) {
            return new JudgeCode();
        } else if (question.getType() == 2) {
            JudgeMulti myJudge = new JudgeMulti();
            if (Objects.equals(question.getScoreMode(), "fix")) {
                myJudge.setStrategy(new FixStrategy());
            } else if (Objects.equals(question.getScoreMode(), "partial")) {
                myJudge.setStrategy(new PartialStrategy());
            } else if (Objects.equals(question.getScoreMode(), "nothing")) {
                myJudge.setStrategy(new NothingStrategy());
            }
            return myJudge;
        }
        return null;
    }
}
