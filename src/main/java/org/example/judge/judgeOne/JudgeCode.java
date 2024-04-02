package org.example.judge.judgeOne;

import org.example.exams.exam.question.Question;
import org.example.judge.judgeOne.codeCompiler.CodeCompiler;
import org.example.judge.judgeOne.codeCompiler.JavaCompile;
import org.example.judge.judgeOne.codeRunner.CodeRunner;
import org.example.judge.judgeOne.codeRunner.JavaRunner;
import org.example.papers.paper.answer.Answer;

import java.nio.file.Paths;
import java.util.List;

public class JudgeCode implements JudgeOne {
    @Override
    public int judgeOne(Question q, List<Answer> an, String path) {
        for (Answer answer : an) {
            CodeCompiler codeCompiler = new JavaCompile();
            CodeRunner codeRunner = new JavaRunner();

            String temp = answer.getAnswer().replace("/", System.getProperty("file.separator"));

            String JavaPath = path + System.getProperty("file.separator") + temp;
            String JavaClassPath = Paths.get(JavaPath).getParent().toString();
            boolean compileResult = codeCompiler.compileCode(JavaPath, JavaClassPath);
            if (!compileResult) {
                answer.setScore(0);
                continue;
            }

            boolean runResult = codeRunner.runCode(JavaClassPath, q.getSamples(), answer.getAnswer().substring(13, answer.getAnswer().length() - 5));

            if (!runResult) {
                answer.setScore(0);
                continue;
            }

            answer.setScore(q.getPoints());
        }
        return 0;
    }

}
