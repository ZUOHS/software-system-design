package org.example.exams.examReader;

import org.example.exams.exam.Exam;
import org.example.exams.exam.question.Question;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlReader extends AbstractReader{
    @Override
    public Exam readExam(String examsPath) {
        Exam exam = new Exam();
        try {
            File file = new File(examsPath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();

            exam.setId(Integer.parseInt(root.getElementsByTagName("id").item(0).getTextContent()));
            exam.setTitle(root.getElementsByTagName("title").item(0).getTextContent());
            exam.setStartTime(Long.parseLong(root.getElementsByTagName("startTime").item(0).getTextContent()));
            exam.setEndTime(Long.parseLong(root.getElementsByTagName("endTime").item(0).getTextContent()));

            NodeList questionNodes = root.getElementsByTagName("question");
            for (int i = 0; i < questionNodes.getLength(); i+=2) {
                Question q = new Question();

                Element questionElement = (Element) questionNodes.item(i);
                q.setId(Integer.parseInt(questionElement.getElementsByTagName("id").item(0).getTextContent()));
                q.setType(Integer.parseInt(questionElement.getElementsByTagName("type").item(0).getTextContent()));
                q.setPoints(Integer.parseInt(questionElement.getElementsByTagName("points").item(0).getTextContent()));
                NodeList answerNodes = questionElement.getElementsByTagName("answer");

                if (q.getType() == 2) {
                    q.setScoreMode(questionElement.getElementsByTagName("scoreMode").item(0).getTextContent());
                    if (q.getScoreMode().equals("fix")) {
                        q.setFixScore(Integer.parseInt(questionElement.getElementsByTagName("fixScore").item(0).getTextContent()));
                    } else if (q.getScoreMode().equals("partial")) {
                        NodeList partialScoreNodes = questionElement.getElementsByTagName("partialScore");
                        for (int j = 0; j < partialScoreNodes.getLength(); j++) {
                            Element partialScoreElement = (Element) partialScoreNodes.item(j);
                            q.addPartialScore(Integer.parseInt(partialScoreElement.getTextContent()));
                        }
                    }
                }

                for (int j = 0; j < answerNodes.getLength(); j++) {
                    Element answerElement = (Element) answerNodes.item(j);
                    String answer = answerElement.getTextContent();
                    q.addQuestion(Integer.parseInt(answer));
                }

                NodeList sampleNodes = questionElement.getElementsByTagName("sample");
                for (int j = 0; j < sampleNodes.getLength(); j++) {
                    Element sampleElement = (Element) sampleNodes.item(j);
                    String input = sampleElement.getElementsByTagName("input").item(0).getTextContent();
                    String output = sampleElement.getElementsByTagName("output").item(0).getTextContent();
                    q.addSample(input, output);
                }
                exam.addQuestion(q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exam;
    }
}
