package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.papers.paper.answer.Answer;

import java.util.List;

public interface JudgeOne {
    int judgeOne(Question q, List<Answer> an, String path);

}
