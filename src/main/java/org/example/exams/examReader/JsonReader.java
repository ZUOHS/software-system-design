package org.example.exams.examReader;

import org.example.exams.exam.Exam;
import org.example.exams.exam.question.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader extends AbstractReader{
    @Override
    public Exam readExam(String examsPath) {
        Exam exam = new Exam();
        try {
            // 读取JSON文件内容为字符串
            String jsonStr = new String(Files.readAllBytes(Paths.get(examsPath)));

            // 将JSON字符串解析为JSONObject
            JSONObject jsonObject = new JSONObject(jsonStr);

            exam.setId(jsonObject.getInt("id"));

            exam.setTitle(jsonObject.getString("title"));

            exam.setStartTime(jsonObject.getLong("startTime"));

            exam.setEndTime(jsonObject.getLong("endTime"));

            JSONArray questions = jsonObject.getJSONArray("questions");
            for (int i = 0; i < questions.length(); i++) {
                Question q = new Question();
                q.setId(questions.getJSONObject(i).getInt("id"));
                q.setType(questions.getJSONObject(i).getInt("type"));
                q.setPoints(questions.getJSONObject(i).getInt("points"));
                q.setPoints(questions.getJSONObject(i).getInt("points"));


                JSONObject question = questions.getJSONObject(i);
                Object modeObject = question.opt("scoreMode");
                if (modeObject instanceof String) {
                    q.setScoreMode((String) modeObject);
                    if (q.getScoreMode().equals("fix")) {
                        q.setFixScore(question.getInt("fixScore"));
                    } else if (q.getScoreMode().equals("partial")) {
                        JSONArray partialScoreArray = question.getJSONArray("partialScore");
                        for (int j = 0; j < partialScoreArray.length(); j++) {
                            q.addPartialScore(partialScoreArray.getInt(j));
                        }
                    }
                }

                Object answerObject = question.opt("answer");

                if (answerObject instanceof Integer) {
                    // 如果答案是一个整数
                    q.addQuestion((int) answerObject);
                } else if (answerObject instanceof JSONArray) {
                    // 如果答案是一个整数数组
                    JSONArray answerArray = (JSONArray) answerObject;
                    for (int j = 0; j < answerArray.length(); j++) {
                        q.addQuestion(answerArray.getInt(j));
                    }
                }

                JSONArray samples = question.optJSONArray("samples");
                if (samples != null) {
                    for (int j = 0; j < samples.length(); j++) {
                        JSONObject sample = samples.getJSONObject(j);
                        String input = sample.getString("input");
                        String output = sample.getString("output");
                        q.addSample(input, output);
                    }
                }

                exam.addQuestion(q);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exam;
    }
}
