package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.judge.judgeOne.codeCalculator.JavaCalculator;
import org.example.judge.judgeOne.codeCompiler.CodeCompiler;
import org.example.judge.judgeOne.codeCompiler.JavaCompile;
import org.example.judge.judgeOne.codeRunner.JavaRunner;
import org.example.judge.thread.CalculateTask;
import org.example.judge.thread.CompileTask;
import org.example.judge.thread.SampleRunTask;
import org.example.judge.thread.SimpleThreadPool;
import org.example.papers.paper.answer.Answer;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;




public class JudgeCode implements JudgeOne {
    private SimpleThreadPool threadPool;

    public JudgeCode() {
        this.threadPool = new SimpleThreadPool(5);
    }

    @Override
    public int judgeOne(Question q, List<Answer> an, String path) {
        for (Answer answer : an) {
            String temp = answer.getAnswer().replace("/", System.getProperty("file.separator"));
            String JavaPath = path + System.getProperty("file.separator") + temp;
            String JavaClassPath = Paths.get(JavaPath).getParent().toString();
            CountDownLatch compileLatch = new CountDownLatch(1);

            CodeCompiler codeCompiler = new JavaCompile();
            CompileTask compileTask = new CompileTask(codeCompiler, JavaPath, JavaClassPath, answer, compileLatch);
            threadPool.submit(compileTask);

            try {
                compileLatch.await(); // Wait for compilation to complete before continuing
                if (answer.getScore() == 0) {
                    continue; // Skip running samples if compilation failed
                }

                CalculateTask calculateTask = new CalculateTask(new JavaCalculator(), JavaPath, answer);
                threadPool.submit(calculateTask);

                answer.setScore(q.getPoints());
                String mainClass = answer.getAnswer().substring(13, answer.getAnswer().length() - 5);
                CountDownLatch runLatch = new CountDownLatch(q.getSamples().size());
                for (String[] sample : q.getSamples()) {
                    SampleRunTask runTask = new SampleRunTask(new JavaRunner(), JavaClassPath, sample, mainClass, answer, runLatch, q.getTimeLimit());
                    threadPool.submit(runTask);
                }
                runLatch.await(); // Wait for all run tasks to complete

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}