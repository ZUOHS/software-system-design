package org.example;

import org.example.exams.Exams;
import org.example.judge.Judge;
import org.example.papers.Papers;

public class Main {
    public static void main(String[] args) {
        String casePath = args[0];
        // 题目文件夹路径
        String examsPath = casePath + System.getProperty("file.separator") + "exams";
        // 答案文件夹路径
        String answersPath = casePath + System.getProperty("file.separator") + "answers";
        // 输出文件路径
        String output = args[1];
        // TODO:在下面调用你实现的功能

        System.out.println(output);
        if (output.endsWith("output.csv")) {
            output = output.substring(0, output.length() - 10);
        } else if (output.endsWith("output_complexity.csv")) {
            output = output.substring(0, output.length() - 21);
        } else {
            System.out.println("Invalid output file name");
            return;
        }

        Exams exams = new Exams();
        Papers papers = new Papers();
        Judge judge = new Judge();

        exams.readExams(examsPath);
        papers.readPapers(answersPath);
        judge.onlineJudge(exams, papers, output, answersPath);
    }
}