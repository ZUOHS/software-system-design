package org.example.judge.judgeOne.codeCompiler;

import javax.tools.*;
import java.util.Arrays;

public class JavaCompile implements CodeCompiler {
    @Override
    public boolean compileCode(String pathJava, String pathClass) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<String> compilationOptions = Arrays.asList("-d", pathClass);

            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(pathJava);

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, compilationOptions, null, compilationUnits);

            boolean success = task.call();

            if (!success) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
