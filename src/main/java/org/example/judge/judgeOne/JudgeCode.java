package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.judge.judgeOne.codeCalculator.CodeCalculator;
import org.example.judge.judgeOne.codeCompiler.CodeCompiler;
import org.example.judge.judgeOne.codeRunner.CodeRunner;
import org.example.judge.thread.CalculateTask;
import org.example.judge.thread.CompileTask;
import org.example.judge.thread.SampleRunTask;
import org.example.judge.thread.SimpleThreadPool;
import org.example.papers.paper.answer.Answer;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.example.judge.judgeOne.codeCalculator.CalculatorFactory.getCalculator;
import static org.example.judge.judgeOne.codeCompiler.CompilerFactory.getCompiler;
import static org.example.judge.judgeOne.codeRunner.RunnerFactory.getRunner;


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

            CodeCalculator codeCalculator = getCalculator(JavaPath);
            CalculateTask calculateTask = new CalculateTask(codeCalculator, JavaPath, answer);
            threadPool.submit(calculateTask);

            if (!answer.isValid()) {
                answer.setScore(0);
                continue;
            }
            CountDownLatch compileLatch = new CountDownLatch(1);

            CodeCompiler codeCompiler = getCompiler(JavaPath);
            CompileTask compileTask = new CompileTask(codeCompiler, JavaPath, JavaClassPath, answer, compileLatch);
            threadPool.submit(compileTask);

            try {
                compileLatch.await(); // Wait for compilation to complete before continuing
                if (answer.getScore() == 0) {
                    continue; // Skip running samples if compilation failed
                }

                answer.setScore(q.getPoints());
                String mainClass = answer.getAnswer().substring(13, answer.getAnswer().length() - 5);
                CountDownLatch runLatch = new CountDownLatch(q.getSamples().size());
                for (String[] sample : q.getSamples()) {
                    CodeRunner codeRunner = getRunner(JavaPath);
                    SampleRunTask runTask = new SampleRunTask(codeRunner, JavaClassPath, sample, mainClass, answer, runLatch, q.getTimeLimit());
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