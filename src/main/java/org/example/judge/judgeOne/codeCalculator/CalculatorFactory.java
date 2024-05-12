package org.example.judge.judgeOne.codeCalculator;

public class CalculatorFactory {
    public static CodeCalculator getCalculator(String path) {
        if (path.endsWith(".java")) {
            return new JavaCalculator();
        }
        return null;
    }
}
