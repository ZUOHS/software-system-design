package org.example.judge;

import org.example.exams.Exams;
import org.example.exams.exam.Exam;
import org.example.exams.exam.question.Question;
import org.example.judge.judgeOne.*;
import org.example.papers.Papers;
import org.example.papers.paper.Paper;
import org.example.papers.paper.answer.Answer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Judge {
    public void onlineJudge(Exams exams, Papers papers, String output, String answersPath) {
        List<Exam> examsList = exams.getExams();
        List<Paper> papersList = papers.getPapers();

        String output1 = output + "output.csv";
        String output2 = output + "output_complexity.csv";


        try {
            File file1 = new File(output1);
            File file2 = new File(output2);

            // 如果文件存在，则清空其内容
            if (file1.exists()) {
                FileWriter fileWriter1 = new FileWriter(file1);
                fileWriter1.write("");
                fileWriter1.close();
            }
            if (file2.exists()) {
                FileWriter fileWriter2 = new FileWriter(file2);
                fileWriter2.write("");
                fileWriter2.close();
            }

            // 创建新文件并打开
            FileWriter fileWriter1 = new FileWriter(file1, true);
            fileWriter1.write("examId, stuId, score\n");

            FileWriter fileWriter2 = new FileWriter(file2, true);
            fileWriter2.write("examId, stuId, qId, complexity\n");


            for (Exam e : examsList) {
                //每一次循环代表一套试卷
                List<Paper> filteredAndSortedPapers = papersList.stream()
                        .filter(paper -> paper.getExamId() == e.getId())
                        .sorted(Comparator.comparingInt(Paper::getStuId))
                        .collect(Collectors.toList());

                List<Question> questionList = e.getQuestions();

                for (Question question :e.getQuestions()) {
                    int id = question.getId();
                    List<Answer> answersToThis = filteredAndSortedPapers.stream()
                            .flatMap(paper -> paper.getAnswersList().stream())
                            .filter(answer -> answer.getId() == id)
                            .collect(Collectors.toList());
                    JudgeOne myJudge = JudgeOneFactory.getJudgeOne(question);
                    myJudge.judgeOne(question, answersToThis, answersPath);
                }


                for (Paper paper : filteredAndSortedPapers) {
                    //每一次循环写入一行，代表一张试卷
                    int score = 0;

                    Long startTime = e.getStartTime();
                    Long endTime = e.getEndTime();
                    Long submitTime = paper.getSubmitTime();


                    List<Answer> an = paper.getAnswersList();
                    for (Question question : questionList) {
                        //每一次循环代表一道题目
                        for (int k = 0; k < an.size(); k++) {
                            if (question.getId() == an.get(k).getId()) {
                                if (question.getType() == 3) {
                                    fileWriter2.write(paper.getExamId() + "," + paper.getStuId() + "," + question.getId() + "," + an.get(k).getComplexity() + "\n");
                                }
                                score += an.get(k).getScore();
                                break;
                            } else if (k == an.size() - 1 && question.getType() == 3) {
                                score += 0;
                                System.out.println("No answer for question " + question.getId());
                            }
                        }
                    }
                    if (submitTime < startTime || submitTime > endTime) {
                        fileWriter1.write(paper.getExamId() + "," + paper.getStuId() + "," + 0 + "\n");
                    } else {
                        fileWriter1.write(paper.getExamId() + "," + paper.getStuId() + "," + score + "\n");
                    }
                }

            }
            fileWriter1.close();
            fileWriter2.close();


        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
