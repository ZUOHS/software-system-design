package org.example.judge.judgeOne.codeCalculator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;


public class JavaCalculator implements CodeCalculator{
    @Override
    public int calculateComplexity(String JavaPath) {
        try {
            FileInputStream in = new FileInputStream(JavaPath);
            JavaParser javaParser = new JavaParser();
            CompilationUnit cu = javaParser.parse(in).getResult().orElseThrow(() -> new RuntimeException("Parsing failed!"));

            AtomicInteger totalComplexity = new AtomicInteger(0); // Start with 1 for the entire class

            cu.accept(new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(MethodDeclaration n, Void arg) {
                    super.visit(n, arg);
                    AtomicInteger complexity = new AtomicInteger(1); // Start with 1 for each method
                    n.accept(new VoidVisitorAdapter<Void>() {
                        @Override
                        public void visit(IfStmt n, Void arg) {
                            super.visit(n, arg);
                            complexity.getAndIncrement(); // for "if"
                            n.getElseStmt().ifPresent(elseStmt -> {
                                if (elseStmt instanceof IfStmt) {
                                    complexity.getAndIncrement(); // for "else if"
                                }
                            });
                        }

                        @Override
                        public void visit(WhileStmt n, Void arg) {
                            super.visit(n, arg);
                            complexity.getAndIncrement(); // for "while"
                        }

                        @Override
                        public void visit(DoStmt n, Void arg) {
                            super.visit(n, arg);
                            complexity.getAndIncrement(); // for "do-while"
                        }

                        @Override
                        public void visit(ForStmt n, Void arg) {
                            super.visit(n, arg);
                            complexity.getAndIncrement(); // for "for"
                        }

                        @Override
                        public void visit(BinaryExpr n, Void arg) {
                            super.visit(n, arg);
                            if (n.getOperator() == BinaryExpr.Operator.AND ||
                                    n.getOperator() == BinaryExpr.Operator.OR) {
                                complexity.getAndIncrement(); // for "&&" or "||"
                            }
                        }

                        @Override
                        public void visit(ConditionalExpr n, Void arg) {
                            super.visit(n, arg);
                            complexity.getAndIncrement(); // for ternary "? :"
                        }
                    }, null);
                    totalComplexity.addAndGet(complexity.get());
                }
            }, null);

            return totalComplexity.get();
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}
