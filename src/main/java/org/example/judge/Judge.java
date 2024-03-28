package org.example.judge;

import org.example.exams.Exams;
import org.example.exams.exam.Exam;
import org.example.exams.exam.question.Question;
import org.example.judge.judgeOne.JudgeCode;
import org.example.judge.judgeOne.JudgeMulti;
import org.example.judge.judgeOne.JudgeSingle;
import org.example.judge.judgeOne.multiStrategy.FixStrategy;
import org.example.judge.judgeOne.multiStrategy.NothingStrategy;
import org.example.judge.judgeOne.multiStrategy.PartialStrategy;
import org.example.papers.Papers;
import org.example.papers.paper.Paper;
import org.example.papers.paper.answer.Answer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Judge {
    public void onlineJudge(Exams exams, Papers papers, String output) {
        List<Exam> examsList = exams.getExams();
        List<Paper> papersList = papers.getPapers();


        try {
            File file = new File(output);

            // 如果文件存在，则清空其内容
            if (file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.close();
            }

            // 创建新文件并打开
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("examId, stuId, score\n");

            for (Exam e : examsList) {
                //每一次循环代表一套试卷
                List<Paper> filteredAndSortedPapers = papersList.stream()
                        .filter(paper -> paper.getExamId() == e.getId())
                        .sorted(Comparator.comparingInt(Paper::getStuId))
                        .collect(Collectors.toList());

                List<Question> questionList = e.getQuestions();
                for (Paper paper : filteredAndSortedPapers) {
                    //每一次循环写入一行，代表一张试卷
                    int score = 0;

                    Long startTime = e.getStartTime();
                    Long endTime = e.getEndTime();
                    Long submitTime = paper.getSubmitTime();
                    if (submitTime < startTime || submitTime > endTime) {
                        fileWriter.write(paper.getExamId() + "," + paper.getStuId() + "," + score + "\n");
                        continue;
                    }


                    List<Answer> an = paper.getAnswersList();

                    for (Question question : questionList) {
                        //每一次循环代表一道题目
                        for (int k = 0; k < an.size(); k++) {
                            if (question.getId() == an.get(k).getId()) {
                                if (question.getType() == 1) {
                                    JudgeOne myJudge = new JudgeSingle();
                                    score += myJudge.judgeOne(question, an.get(k));
                                } else if (question.getType() == 3) {
                                    JudgeOne myJudge = new JudgeCode();
                                    score += myJudge.judgeOne(question, an.get(k));
                                } else if (question.getType() == 2) {
                                    JudgeMulti myJudge = new JudgeMulti();
                                    if (Objects.equals(question.getScoreMode(), "fix")) {
                                        myJudge.setStrategy(new FixStrategy());
                                    } else if (Objects.equals(question.getScoreMode(), "partial")) {
                                        myJudge.setStrategy(new PartialStrategy());
                                    } else if (Objects.equals(question.getScoreMode(), "nothing")) {
                                        myJudge.setStrategy(new NothingStrategy());
                                    }
                                    score += myJudge.judgeOne(question, an.get(k));
                                }
                                break;
                            } else if (k == an.size() - 1 && question.getType() == 3) {
                                score += question.getPoints();
                            }
                        }
                    }
                    fileWriter.write(paper.getExamId() + "," + paper.getStuId() + "," + score + "\n");
                }
            }
            fileWriter.close();
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
