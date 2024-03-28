package org.example.exams;

import lombok.Getter;
import org.example.exams.exam.Exam;
import org.example.exams.examReader.AbstractReader;
import org.example.exams.examReader.ReaderFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Exams {

    @Getter
    private List<Exam> exams;

    public Exams() {
        exams = new ArrayList<>();
    }

    public void readExams(String examsPath) {
        File folder = new File(examsPath);
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String filePath = examsPath + "\\" + file.getName();
            if (file.isFile()) {
                //使用简单工厂设计模式，读取不同的文件类型
                AbstractReader reader = ReaderFactory.getAbstractReader(filePath);
                Exam temp = null;
                if (reader != null) {
                    temp = reader.readExam(filePath);
                }
                addExam(temp);
            }
        }
    }

    public void addExam(Exam e) {
        exams.add(e);
    }
}
