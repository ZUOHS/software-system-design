package org.example.exams.examReader;

import org.example.exams.exam.Exam;

public abstract class AbstractReader {
    public abstract Exam readExam(String examsPath);
}
