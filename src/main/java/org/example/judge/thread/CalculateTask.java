package org.example.judge.thread;

import org.example.judge.judgeOne.codeCalculator.CodeCalculator;
import org.example.papers.paper.answer.Answer;

public class CalculateTask implements Task {
    private final CodeCalculator calculator;
    private final String javaPath;
    private final Answer answer;

    public CalculateTask(CodeCalculator calculator, String javaPath, Answer answer) {
        this.calculator = calculator;
        this.javaPath = javaPath;
        this.answer = answer;
    }

    @Override
    public void execute() {
        try {
            int success = calculator.calculateComplexity(javaPath);
            synchronized (answer) {
                answer.setComplexity(success);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

