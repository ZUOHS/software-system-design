package org.example.papers.paperReader;

import org.example.papers.paper.Paper;
import org.example.papers.paper.answer.Answer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PaperReader {
    public Paper readPaper(String filePath) {
        Paper paper = new Paper();
        try {
            // 读取JSON文件内容为字符串
            String jsonStr = new String(Files.readAllBytes(Paths.get(filePath)));

            // 将JSON字符串解析为JSONObject
            JSONObject jsonObject = new JSONObject(jsonStr);

            paper.setExamId(jsonObject.getInt("examId"));

            paper.setStuId(jsonObject.getInt("stuId"));

            paper.setSubmitTime(jsonObject.getLong("submitTime"));


            JSONArray answers = jsonObject.getJSONArray("answers");
            for (int i = 0; i < answers.length(); i++) {
                Answer an = new Answer();
                an.setId(answers.getJSONObject(i).getInt("id"));
                an.setAnswer(answers.getJSONObject(i).getString("answer"));
                paper.addAnswer(an);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paper;
    }
}
