package org.example.judge.judgeOne.codeRunner;


public class RunnerFactory {
    public static CodeRunner getRunner(String path) {
        if (path.endsWith(".java")) {
            return new JavaRunner();
        }
        return null;
    }
}
