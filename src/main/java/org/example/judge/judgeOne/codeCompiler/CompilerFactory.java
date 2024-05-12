package org.example.judge.judgeOne.codeCompiler;

public class CompilerFactory {
    public static CodeCompiler getCompiler(String path) {
        if (path.endsWith(".java")) {
            return new JavaCompile();
        }
        return null;
    }
}
