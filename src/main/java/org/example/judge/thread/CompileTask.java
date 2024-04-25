package org.example.judge.thread;

import org.example.judge.judgeOne.codeCompiler.CodeCompiler;
import org.example.papers.paper.answer.Answer;

import java.util.concurrent.CountDownLatch;

public class CompileTask implements Task {
    private final CodeCompiler compiler;
    private final String javaPath;
    private final String javaClassPath;
    private final Answer answer;
    private final CountDownLatch latch;

    public CompileTask(CodeCompiler compiler, String javaPath, String javaClassPath, Answer answer, CountDownLatch latch) {
        this.compiler = compiler;
        this.javaPath = javaPath;
        this.javaClassPath = javaClassPath;
        this.answer = answer;
        this.latch = latch;
    }

    @Override
    public void execute() {
        try {
            boolean success = compiler.compileCode(javaPath, javaClassPath);
            synchronized (answer) {
                if (!success) {
                    answer.setScore(0);
                    answer.setComplexity(-1);
                }
            }
        } finally {
            latch.countDown();
        }
    }
}

