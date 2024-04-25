package org.example.judge.thread;

import org.example.judge.judgeOne.codeRunner.CodeRunner;
import org.example.papers.paper.answer.Answer;

import java.util.concurrent.CountDownLatch;

public class SampleRunTask implements Task {
    private final CodeRunner runner;
    private final String classPath;
    private final String[] sample;
    private final String mainClass;
    private final Answer answer;

    private final CountDownLatch latch;

    private final int limit;

    public SampleRunTask(CodeRunner runner, String classPath, String[] sample, String mainClass, Answer answer, CountDownLatch latch, int limit) {
        this.runner = runner;
        this.classPath = classPath;
        this.sample = sample;
        this.mainClass = mainClass;
        this.answer = answer;
        this.latch = latch;
        this.limit = limit;
    }

    @Override
    public void execute() {
        try {
            boolean success = runner.runCode(classPath, sample, mainClass, limit);
            synchronized (answer) {
                if (!success) {
                    answer.setScore(0);
                }
            }
        } finally {
            latch.countDown();
        }
    }
}