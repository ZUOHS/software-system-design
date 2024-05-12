package org.example.papers.paper.answer;

import lombok.Data;

@Data
public class Answer {
    private int id;
    private String answer;
    private int score;
    private int complexity;
    private boolean valid;
    public Answer() {
        score = -1;
        complexity = -2;
    }
}
