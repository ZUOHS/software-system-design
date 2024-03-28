package org.example.judge.judgeOne.multiStrategy;

import org.example.exams.exam.question.Question;
import org.example.papers.paper.answer.Answer;

public interface strategy {
    int calPartial(Question q, Answer an);
}
